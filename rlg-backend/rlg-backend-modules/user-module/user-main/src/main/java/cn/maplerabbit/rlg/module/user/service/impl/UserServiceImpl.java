package cn.maplerabbit.rlg.module.user.service.impl;

import cn.maplerabbit.rlg.common.constpak.LoginPrincipalConst;
import cn.maplerabbit.rlg.common.enumpak.AccountType;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.module.user.exception.UserException;
import cn.maplerabbit.rlg.common.util.IpUtil;
import cn.maplerabbit.rlg.common.util.JwtUtil;
import cn.maplerabbit.rlg.module.log.service.IUserLoginLogService;
import cn.maplerabbit.rlg.module.user.mapper.UserMapper;
import cn.maplerabbit.rlg.module.user.service.IRoleService;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.log.entity.UserLoginLog;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.pojo.user.entity.User;
import cn.maplerabbit.rlg.common.property.JwtProperties;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl
        implements IUserService,
                   LoginPrincipalConst
{
    @Autowired
    private JwtProperties jwtProperties;
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
    private JwtUtil jwtUtil;

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

        claims.put(CLAIMS_KEY_UUID, details.getUuid());
        claims.put(CLAIMS_KEY_USERNAME, details.getUsername());
        claims.put(CLAIMS_KEY_PHONE, details.getPhone());
        claims.put(CLAIMS_KEY_EMAIL, details.getEmail());
        claims.put(CLAIMS_KEY_IP, details.getIp());
        claims.put(CLAIMS_KEY_AVATAR_URL, details.getAvatarUrl());
        claims.put(CLAIMS_KEY_AUTHORITIES, JSON.toJSONString(details.getAuthorities()));

        LocalDateTime now = LocalDateTime.now();

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

        userLoginLogService.addLog(
                new UserLoginLog()
                        .setUserUuid(details.getUuid())
                        .setUsername(details.getUsername())
                        .setIp(details.getIp())
                        .setEngine(request.getHeader(USER_AGENT))
                        .setTime(now)
        );

        // 存入JWT并返回
        return jwtUtil.generate(claims);
    }

    @Override
    public UserDetails loadUser(String field, String account)
    {

        UserLoginVO info = userMapper.loadUserByCustomField(field, account);

        log.trace("Get LoginInfo matches {}【{}】: {}", field, account, info);

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
        Claims body = Jwts
                .parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(jwt)
                .getBody();

        // 使用jwt中的数据重新生成jwt
        return jwtUtil.generate(body);
    }
}