package cn.maplerabbit.rlg.module.user.service.impl;

import cn.maplerabbit.rlg.common.constpak.LoginPrincipalConst;
import cn.maplerabbit.rlg.common.enumpak.AccountType;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.ProgramError;
import cn.maplerabbit.rlg.common.exception.ServiceException;
import cn.maplerabbit.rlg.module.user.exception.UserException;
import cn.maplerabbit.rlg.common.util.IpUtil;
import cn.maplerabbit.rlg.common.util.TokenUtil;
import cn.maplerabbit.rlg.module.log.service.IUserLoginLogService;
import cn.maplerabbit.rlg.module.user.mapper.UserMapper;
import cn.maplerabbit.rlg.module.user.service.IRoleService;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.log.entity.UserLoginLog;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.pojo.user.entity.User;
import cn.maplerabbit.rlg.common.property.TokenProperties;
import cn.maplerabbit.rlg.common.security.UserDetails;
import cn.maplerabbit.rlg.pojo.user.vo.UserLoginVO;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
@Slf4j
public class UserServiceImpl
        implements IUserService,
                   LoginPrincipalConst
{
    @Autowired
    private TokenProperties tokenProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IUserLoginLogService userLoginLogService;
    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 存储浏览器内核信息的请求头
     */
    private static final String USER_AGENT = "user-agent";

    public UserServiceImpl()
    {
        log.debug("UserServiceImpl()...");
    }

    @Override
    @Transactional
    public void register(UserRegisterDTO userRegisterDTO)
    {
        // 按照用户名查询用户个数，若查询到数据说明用户名已存在
        if (
                userMapper
                        .countByCustomField(
                                AccountType.USERNAME.getField(),
                                userRegisterDTO.getUsername()
                        )
                        > 0
        )
            throw new UserException(ServiceCode.ERR_CONFLICT, "用户名已存在");

        // 创建新的用户对象
        User user = new User()
                .setUuid(
                        UUID
                                .randomUUID()
                                .toString()
                                .replaceAll("-", "")
                )
                .setUsername(
                        userRegisterDTO.getUsername()
                )
                .setPassword(
                        passwordEncoder.encode(
                                userRegisterDTO.getPassword()
                        )
                )
                .setGender(1)
                .setEnable(1)
                .setSignUpTime(LocalDateTime.now());

        // 向数据库写入用户
        userMapper.save(user);
        // 向数据库写入用户与用户角色关联数据，默认为普通用户
        roleService.bindDefaultRole(user.getUuid());
    }

    @Override
    @Transactional
    public String login(UserDetails details)
    {
        Map<String, Object> claims = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Date timeout =
                new Date(
                        System.currentTimeMillis() +
                                MILLISECONDS.convert(tokenProperties.getUsableMinutes(), TimeUnit.MINUTES)
                );

        // 准备token要保存的信息
        claims.put(CLAIMS_KEY_UUID, details.getUuid());                                     // 用户uuid
        claims.put(CLAIMS_KEY_USERNAME, details.getUsername());                             // 密码
        claims.put(CLAIMS_KEY_PHONE, details.getPhone());                                   // 手机号
        claims.put(CLAIMS_KEY_EMAIL, details.getEmail());                                   // 邮箱
        claims.put(CLAIMS_KEY_IP, details.getIp());                                         // 当前登录ip
        claims.put(CLAIMS_KEY_AVATAR_URL, details.getAvatarUrl());                          // 用户头像
        claims.put(CLAIMS_KEY_AUTHORITIES, JSON.toJSONString(details.getAuthorities()));    // 权限列表
        claims.put(CLAIMS_KEY_TIMEOUT, dateFormat.format(timeout));                         // 过期时间

        // 更新最后登录时间
        if (
                userMapper.updateLogin(
                        details.getUuid(),
                        now,
                        details.getIp()
                )
                        < 1
        )
            log.warn("UserMapper update login failed, user uuid: {}, username: {}", details.getUuid(), details.getUsername());

        // 记录用户登录日志
        userLoginLogService.addLog(
                new UserLoginLog()
                        .setUserUuid(details.getUuid())
                        .setUsername(details.getUsername())
                        .setIp(details.getIp())
                        .setEngine(request.getHeader(USER_AGENT))
                        .setTime(now)
        );

        // 生成token并返回
        return tokenUtil.generate(claims, timeout);
    }

    @Override
    public UserDetails loadUser(String field, String account)
    {

        UserLoginVO info = userMapper.loadUserByCustomField(field, account);

        log.trace("Get LoginInfo matches '{}'【{}】: \n{}", field, account, info);

        // 创建UserDetails并返回
        return new UserDetails(
                info.getUuid(),
                info.getUsername(),
                info.getPassword(),
                info.getPhone(),
                info.getEmail(),
                IpUtil.getIp(request),
                info.getAvatarUrl(),
                info.getEnable() == 1,
                // 生成权限列表
                info.getPermissions()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(
                                Collectors.toList()
                        )
        );
    }

    @Override
    public String refresh(String jwt)
    {
        // 获取token保存的信息
        Claims body = Jwts
                .parser()
                .setSigningKey(tokenProperties.getSecretKey())
                .parseClaimsJws(jwt)
                .getBody();

        // 准备新的过期时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try
        {
            // 如果token距离过期时间超过一天，拒绝刷新
            if (
                    (dateFormat.parse((String) body.get(CLAIMS_KEY_TIMEOUT)).getTime() - new Date().getTime()) <
                    MILLISECONDS.convert(tokenProperties.getRefreshTime(), TimeUnit.MINUTES)
                    // 服务端稍微放宽时限，避免误差产生的重复请求
            )
                throw new ServiceException(ServiceCode.ERR_BAD_REQUEST, "token尚未临期，拒绝刷新！");
        }
        catch (ParseException e)
        {
            log.error("-- ParseException, msg: {}", e.getMessage());
            throw new ProgramError(e.getMessage());
        }

        Date timeout =
                new Date(
                        System.currentTimeMillis() +
                                MILLISECONDS.convert(tokenProperties.getUsableMinutes(), TimeUnit.MINUTES)
                );

        // 覆盖旧的过期时间
        body.put(CLAIMS_KEY_TIMEOUT, dateFormat.format(timeout));

        // 重新生成jwt
        return tokenUtil.generate(body, timeout);
    }
}