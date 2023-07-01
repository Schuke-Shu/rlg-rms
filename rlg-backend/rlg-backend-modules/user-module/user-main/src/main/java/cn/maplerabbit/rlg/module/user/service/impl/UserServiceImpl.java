package cn.maplerabbit.rlg.module.user.service.impl;

import cn.maplerabbit.rlg.common.constpak.LoginPrincipalConst;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.UserException;
import cn.maplerabbit.rlg.module.log.service.IUserLoginLogService;
import cn.maplerabbit.rlg.module.user.mapper.UserMapper;
import cn.maplerabbit.rlg.module.user.service.ILoginInfoService;
import cn.maplerabbit.rlg.module.user.service.IRoleService;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.log.entity.UserLoginLog;
import cn.maplerabbit.rlg.pojo.user.dto.UserEmailLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UsernameLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.pojo.user.entity.User;
import cn.maplerabbit.rlg.pojo.user.vo.UserInfoVO;
import cn.maplerabbit.rlg.common.property.JwtProperties;
import cn.maplerabbit.rlg.common.entity.UserDetails;
import cn.maplerabbit.rlg.common.util.ValidationCodeUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl
        implements IUserService,
                   LoginPrincipalConst
{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ValidationCodeUtil validationCodeUtil;
    @Autowired
    private ILoginInfoService loginService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IUserLoginLogService userLoginLogService;

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
        if (userMapper.countByUsername(userRegisterDTO.getUsername()) > 0)
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
    public String login(UsernameLoginDTO userLoginDTO)
    {
        // 认证
        UserDetails userDetails =
                (UserDetails) authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLoginDTO.getUsername(),
                                userLoginDTO.getPassword()
                        )
                )
                        .getPrincipal();
        log.trace("Get UserDetails: {}", userDetails);

        // 存入JWT并返回
        return generateJWT(
                handleLogin(userDetails)
        );
    }

    @Override
    public void getEmailLoginCode(String email)
    {
        validationCodeUtil.sendEmail(email);
    }

    @Override
    @Transactional
    public String emailLogin(UserEmailLoginDTO userEmailLoginDTO)
    {
        validationCodeUtil.validate(
                userEmailLoginDTO.getEmail(),
                userEmailLoginDTO.getCode()
        );

        // 获取用户数据
        UserDetails userDetails = loginService.loadUserByEmail(userEmailLoginDTO.getEmail());

        log.debug("Get UserDetails: {}", userDetails);

        // 存入JWT并返回
        return generateJWT(
                handleLogin(userDetails)
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
        return generateJWT(body);
    }

    @Override
    public UserInfoVO getUserInfo(String uuid)
    {
        // 查询user对象
        User user = userMapper.query(uuid);
        // 若user为null，抛出异常
        if (user == null)
            throw new UserException(ServiceCode.ERR_NOT_FOUND, "找不到该用户");
        // 创建UserInfoVO
        UserInfoVO vo = new UserInfoVO();
        // 将user的属性复制给vo对象
        BeanUtils.copyProperties(user, vo);
        // 返回
        return vo;
    }

    /**
     * 获取用户登录信息并做相关处理（更改最后登录时间、记录日志等）
     *
     * @param userDetails 用户信息
     * @return 存储用户信息的map
     */
    private Map<String, Object> handleLogin(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIMS_KEY_UUID, userDetails.getUuid());
        claims.put(CLAIMS_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIMS_KEY_PHONE, userDetails.getPhone());
        claims.put(CLAIMS_KEY_EMAIL, userDetails.getEmail());
        claims.put(CLAIMS_KEY_IP, userDetails.getIp());
        claims.put(CLAIMS_KEY_AUTHORITIES, JSON.toJSONString(userDetails.getAuthorities()));

        LocalDateTime now = LocalDateTime.now();

        if (
                userMapper.updateLogin(
                        userDetails.getUuid(),
                        now,
                        userDetails.getIp()
                )
                        != 1
        )
            log.warn("UserMapper update login failed, user uuid: {}, username: {}", userDetails.getUuid(), userDetails.getUsername());

        userLoginLogService.addLog(
                new UserLoginLog()
                        .setUserUuid(userDetails.getUuid())
                        .setUsername(userDetails.getUsername())
                        .setIp(userDetails.getIp())
                        .setEngine(request.getHeader(USER_AGENT))
                        .setTime(now)
        );

        return claims;
    }

    /**
     * 生成jwt
     *
     * @param claims 要存储的数据
     * @return JWT字符串
     */
    private String generateJWT(Map<String, Object> claims)
    {
        return Jwts.builder() // 获取JwtBuilder，用于构建JWT
                // 配置Header
                .setHeaderParam("alg", jwtProperties.getAlgorithm())
                .setHeaderParam("typ", jwtProperties.getType())
                // 配置payload（存入数据）
                .setClaims(claims)
                // 配置Signature
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + (long) jwtProperties.getUsableMinutes() * 60 * 1000
                        )
                ) // JWT过期时间
                .signWith(
                        SignatureAlgorithm.forName(
                                jwtProperties.getAlgorithm()
                        ),
                        jwtProperties.getSecretKey()
                )
                .compact(); // 获取JWT
    }
}