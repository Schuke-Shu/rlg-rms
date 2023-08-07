package cn.maplerabbit.rlg.security;

import cn.maplerabbit.rlg.common.entity.UserDetails;
import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.CodeException;
import cn.maplerabbit.rlg.common.util.IAccountUtil;
import cn.maplerabbit.rlg.common.util.ICodeUtil;
import cn.maplerabbit.rlg.common.util.IErrorUtil;
import cn.maplerabbit.rlg.common.util.IRedisUtil;
import cn.maplerabbit.rlg.module.user.exception.UserException;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证类
 */
@Slf4j
@Component
public class LoginAuthenticationProvider
        implements AuthenticationProvider
{
    public static final String LOGIN_WAY_PWD = "pwd";
    public static final String LOGIN_WAY_CODE = "code";

    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAccountUtil accountUtil;
    @Autowired
    private ICodeUtil codeUtil;
    @Autowired
    private IRedisUtil<String> redisUtil;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IErrorUtil errorUtil;

    public LoginAuthenticationProvider() {log.debug("LoginAuthenticationProvider()...");}

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        if (authentication.isAuthenticated()) return null;

        LoginAuthenticationToken token = (LoginAuthenticationToken) authentication;

        // 账户
        String account = (String) token.getPrincipal();
        // 密钥（验证码或密码）
        String key = (String) token.getCredentials();
        // 登录方式
        String loginWay = token.getLoginWay();

        if (!StringUtils.hasText(loginWay))
            throw new AuthenticationServiceException("须要提供LoginWay请求头");

        // 若是验证码登录，进行验证码验证
        if (loginWay.equals(LOGIN_WAY_CODE))
        {
            log.debug("Validating code...");
            // 用于获取redis中存储的验证码的key
            String k = redisUtil.key(
                    request.getRequestURI(),
                    account
            );
            log.debug("Get code from redis, key: {}", k);

            try
            {
                // 验证
                codeUtil.validate(key, redisUtil.get(k));
                // 验证成功，删除验证码
                redisUtil.remove(k);
            }
            catch (CodeException e)
            {
                // 验证失败，报错
                errorUtil.response(ErrorResult.fail(e));
                return null;
            }
        }

        UserDetails user = null;
        try
        {
            user = userService
                    .loadUser(accountUtil.parse(account).field(), account);
        }
        catch (UserException e)
        {
            errorUtil.response(ErrorResult.fail(e));
            return null;
        }

        if (
                // 用户对象为空
                user == null ||
                // 登陆方式为密码登录，且密码错误
                (
                        loginWay.equals(LOGIN_WAY_PWD) &&
                        !passwordEncoder.matches(key, user.getPassword())
                )
        )
            errorUtil.response(
                    ErrorResult.fail(ServiceCode.ERR_UNAUTHORIZED, "登录错误，请检查登录信息")
            );

        LoginAuthenticationToken result =
                new LoginAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities()
                );

        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}