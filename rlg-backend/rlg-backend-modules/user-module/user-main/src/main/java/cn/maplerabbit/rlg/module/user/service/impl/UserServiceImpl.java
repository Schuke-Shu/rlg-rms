package cn.maplerabbit.rlg.module.user.service.impl;

import cn.maplerabbit.rlg.constpak.LoginPrincipalConst;
import cn.maplerabbit.rlg.entity.AssociateUnit;
import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.UserException;
import cn.maplerabbit.rlg.module.user.mapper.RoleMapper;
import cn.maplerabbit.rlg.module.user.mapper.UserMapper;
import cn.maplerabbit.rlg.module.user.mapper.UserRoleMapper;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.user.dto.UserLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.pojo.user.entity.User;
import cn.maplerabbit.rlg.pojo.user.vo.UserInfoVO;
import cn.maplerabbit.rlg.property.JwtProperties;
import cn.maplerabbit.rlg.entity.UserDetails;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 普通用户角色
     */
    private static final String ROLE_USER = "ROLE_USER";

    public UserServiceImpl()
    {
        log.debug("UserServiceImpl()...");
    }

    /**
     * 用户名注册
     * @param userRegisterDTO
     * @errorCode - adfads
     */
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
        userRoleMapper.save(
                new AssociateUnit<>(
                        user.getId(),
                        roleMapper.queryByFlag(ROLE_USER).getId()
                )
        );
    }

    /**
     * 用户名登录
     *
     * @return JWT字符串
     */
    @Override
    public String login(UserLoginDTO userLoginDTO)
    {
        // 认证
        Authentication authenticateResult =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLoginDTO.getUsername(),
                                userLoginDTO.getPassword()
                        )
                );
        log.trace("authenticateResult: {}", authenticateResult);

        // 取出数据
        UserDetails adminDetails = (UserDetails) authenticateResult.getPrincipal();
        // 数据存入map
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_KEY_ID, adminDetails.getId());
        claims.put(CLAIMS_KEY_USERNAME, adminDetails.getUsername());
        claims.put(CLAIMS_KEY_PHONE, adminDetails.getPhone());
        claims.put(CLAIMS_KEY_EMAIL, adminDetails.getEmail());
        claims.put(CLAIMS_KEY_IP, adminDetails.getIp());
        claims.put(CLAIMS_KEY_AUTHORITIES, JSON.toJSONString(adminDetails.getAuthorities()));

        // 存入JWT并返回
        return generateJWT(claims);
    }

    @Override
    public String refresh(String jwt)
    {
        return generateJWT(
                Jwts
                        .parser()
                        .setSigningKey(jwtProperties.getSecretKey())
                        .parseClaimsJws(jwt)
                        .getBody()
        );
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
                .setHeaderParam("alg", "HS256") // alg（algorithm：算法）
                .setHeaderParam("typ", "JWT")   // typ（type：类型）
                // 配置payload（存入数据）
                .setClaims(claims)
                // 配置Signature
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + (long) jwtProperties.getUsableMinutes() * 60 * 1000
                        )
                ) // JWT过期时间
                .signWith(
                        SignatureAlgorithm.HS256, // 签名算法
                        jwtProperties.getSecretKey()
                )
                .compact(); // 获取JWT
    }

    /**
     * 获取用户信息
     */
    @Override
    public UserInfoVO getUserInfo(Long id)
    {
        // 查询user对象
        User user = userMapper.query(id);
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
}