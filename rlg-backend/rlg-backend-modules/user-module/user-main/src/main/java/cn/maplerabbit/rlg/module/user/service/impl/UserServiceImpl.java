package cn.maplerabbit.rlg.module.user.service.impl;

import cn.maplerabbit.rlg.constpak.LoginPrincipalConst;
import cn.maplerabbit.rlg.entity.AssociateUnit;
import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.UserException;
import cn.maplerabbit.rlg.module.user.mapper.RoleMapper;
import cn.maplerabbit.rlg.module.user.mapper.UserMapper;
import cn.maplerabbit.rlg.module.user.mapper.UserRoleMapper;
import cn.maplerabbit.rlg.module.user.service.ILoginInfoService;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.user.dto.UserEmailLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UsernameLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.pojo.user.entity.User;
import cn.maplerabbit.rlg.pojo.user.vo.UserInfoVO;
import cn.maplerabbit.rlg.property.JwtProperties;
import cn.maplerabbit.rlg.entity.UserDetails;
import cn.maplerabbit.rlg.util.ValidationCodeUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private HttpSession session;
    @Autowired
    private ValidationCodeUtil validationCodeUtil;
    @Autowired
    private ILoginInfoService loginService;

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
        userRoleMapper.save(
                new AssociateUnit<>(
                        user.getId(),
                        roleMapper.queryByFlag(ROLE_USER).getId()
                )
        );
    }

    @Override
    public String login(UsernameLoginDTO userLoginDTO)
    {
        // 认证
        UserDetails userDetails =
                (UserDetails) authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLoginDTO.getUsername(),
                                userLoginDTO.getPassword()
                        )
                );
        log.trace("Get UserDetails: {}", userDetails);

        // 存入JWT并返回
        return generateJWT(
                getClaims(userDetails)
        );
    }

    @Override
    public void sendEmailLoginCode(String email)
    {
        // 向目标邮箱发送验证码
        String code = validationCodeUtil.sendEmail(email);
        // 将验证码绑定到session中
        session.setAttribute(EMAIL_LOGIN_CODE_ID, code);
    }

    @Override
    public String emailLogin(UserEmailLoginDTO userEmailLoginDTO)
    {
        // 获取存储在session中的验证码
        String code = (String) session.getAttribute(EMAIL_LOGIN_CODE_ID);

        // 验证
        if (code == null)
            throw new UserException(ServiceCode.ERR_NOT_FOUND, "请先获取验证码");
        if (!code.equals(userEmailLoginDTO.getCode()))
            throw new UserException(ServiceCode.ERR_BAD_REQUEST, "验证码不正确");

        // 获取用户数据
        UserDetails userDetails = loginService.loadUserByEmail(userEmailLoginDTO.getEmail());

        log.debug("Get UserDetails: {}", userDetails);

        // 存入JWT并返回
        return generateJWT(
                getClaims(userDetails)
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

    private Map<String, Object> getClaims(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();

        // 获取用户数据并存入map
        claims.put(CLAIMS_KEY_ID, userDetails.getId());
        claims.put(CLAIMS_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIMS_KEY_PHONE, userDetails.getPhone());
        claims.put(CLAIMS_KEY_EMAIL, userDetails.getEmail());
        claims.put(CLAIMS_KEY_IP, userDetails.getIp());
        claims.put(CLAIMS_KEY_AUTHORITIES, JSON.toJSONString(userDetails.getAuthorities()));

        // 返回
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
                .setHeaderParam("alg", SignatureAlgorithm.HS256.getValue()) // alg（algorithm：算法）
                .setHeaderParam("typ", JWT_TYPE)   // typ（type：类型）
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
}