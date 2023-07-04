package cn.maplerabbit.rlg.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static cn.maplerabbit.rlg.common.util.FilterError.error;

/**
 * 登录验证过滤器
 * <p>客户端登录时需要提供一个"LoginWay"的请求头，值为"pwd"或"code"，表明是通过密码还是验证码登录</p>
 */
@Slf4j
public class LoginAuthenticationFilter
        extends AbstractAuthenticationProcessingFilter
{
    public static final String LOGIN_WAY_HEADER = "login-way";
    public static final String LOGIN_ACCOUNT = "account";
    public static final String LOGIN_KEY = "key";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/user/login", "POST");
    private String accountParameter = LOGIN_ACCOUNT;
    private String keyParameter = LOGIN_KEY;
    private boolean postOnly = true;

    public LoginAuthenticationFilter()
    {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        log.debug("LoginAuthenticationFilter()...");
    }

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        log.debug("LoginAuthenticationFilter()...");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        // 登录账户，可能为用户名、邮箱、手机号
        String account = obtainAccount(request);
        // 登录密钥，可能为密码、验证码
        String key = obtainKey(request);
        // 登录方式
        String loginWay = request.getHeader(LOGIN_WAY_HEADER);
        // 请求方法
        String method = request.getMethod();

        log.trace(
                "Entry LoginAuthenticationFilter, details: \nlogin way: {}\naccount: {}, key: {}\nrequest method: {}",
                loginWay,
                account,
                key,
                method
        );

        // 登录方法只允许post
        if (this.postOnly && !method.equals("POST"))
        {
            log.debug("The request method is '{}', it's not supported", method);
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        LoginAuthenticationToken authRequest =
                new LoginAuthenticationToken(
                        account != null ? account : "",
                        key != null ? key : "",
                        loginWay
                );

        setDetails(request, authRequest);

        Authentication authentication = null;
        try
        {
            authentication = getAuthenticationManager()
                    .authenticate(authRequest);
        }
        catch (AuthenticationException e)
        {
            log.debug("-- AuthenticationException, msg: {}", e.getMessage());
            error(response, ErrorResult.fail(ServiceCode.ERR_UNAUTHORIZED, e.getMessage()));
        }

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        setContinueChainBeforeSuccessfulAuthentication(true);
        return authentication;
    }

    /**
     * 从请求中获取账户
     */
    @Nullable
    protected String obtainAccount(HttpServletRequest request)
    {
        return request.getParameter(this.accountParameter);
    }

    /**
     * 从请求中获取密钥
     */
    @Nullable
    protected String obtainKey(HttpServletRequest request)
    {
        return request.getParameter(this.keyParameter);
    }

    protected void setDetails(HttpServletRequest request, LoginAuthenticationToken authRequest)
    {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Autowired
    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler)
    {
        super.setAuthenticationFailureHandler(failureHandler);
    }

    public final String getAccountParameter()
    {
        return this.accountParameter;
    }

    public final String getKeyParameter()
    {
        return this.keyParameter;
    }

    public void setAccountParameter(String accountParameter)
    {
        Assert.hasText(accountParameter, "Account parameter must not be empty or null");
        this.accountParameter = accountParameter;
    }

    public void setKeyParameter(String keyParameter)
    {
        Assert.hasText(keyParameter, "Key parameter must not be empty or null");
        this.keyParameter = keyParameter;
    }

    public void setPostOnly(boolean postOnly)
    {
        this.postOnly = postOnly;
    }

    public boolean isPostOnly()
    {
        return postOnly;
    }
}