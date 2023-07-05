package cn.maplerabbit.rlg.common.security;

import cn.maplerabbit.rlg.common.enumpak.AccountType;
import cn.maplerabbit.rlg.common.exception.CodeException;
import cn.maplerabbit.rlg.common.util.ValidationCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    private ILoginService loginRegisterService;
    @Autowired
    private ValidationCodeUtil validationCodeUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
            try
            {
                log.debug("Validating code...");
                validationCodeUtil.validate(account, key);
            }
            catch (CodeException e)
            {
                log.debug("-- CodeException, msg: {}", e.getMessage());
                throw new AuthenticationServiceException(e.getMessage());
            }
        }

        UserDetails user = loginRegisterService
                .loadUser(AccountType.parse(account).getField(), account);

        if (
                // 用户对象为空
                user == null ||
                // 登陆方式为密码登录，且密码错误
                (
                        loginWay.equals(LOGIN_WAY_PWD) &&
                        !passwordEncoder.matches(key, user.getPassword())
                )
        )
            throw new AuthenticationServiceException("登录错误，请检查登录信息");

        LoginAuthenticationToken result =
                new LoginAuthenticationToken(user,user.getPassword(),user.getAuthorities());

        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}