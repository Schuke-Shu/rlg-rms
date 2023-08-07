package cn.maplerabbit.rlg.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static cn.maplerabbit.rlg.common.property.RlgProperties.UserProperties.LoginProperties;

/**
 * 登录验证过滤器
 * <p>客户端登录时需要提供一个"LoginWay"的请求头，值为"pwd"或"code"，表明是通过密码还是验证码登录</p>
 */
@Slf4j
public class LoginAuthenticationFilter
        extends AbstractAuthenticationProcessingFilter
{
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/user/login", "POST");
    private boolean postOnly = true;

    private final LoginProperties loginProperties;

    private static final String ACCOUNT_PARAM = "account";
    private static final String KEY_PARAM = "key";

    public LoginAuthenticationFilter(LoginProperties loginProperties, AuthenticationManager authenticationManager)
    {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.loginProperties = loginProperties;
        log.debug("LoginAuthenticationFilter()...");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        log.debug("Access LoginAuthenticationFilter");

        // 登录账户，可能为用户名、邮箱、手机号
        String account = obtainAccount(request);
        // 登录密钥，可能为密码、验证码
        String key = obtainKey(request);
        // 登录方式
        String loginWay = request.getHeader(loginProperties.getLoginWayHeader());
        // 请求方法
        String method = request.getMethod();

        log.trace(
                "details: \nlogin way: {}\naccount: {}, key: {}\nrequest method: {}",
                loginWay,
                account,
                key,
                method
        );

        // 登录方法只允许post
        if (this.postOnly && !method.equals("POST"))
        {
            log.debug("Authentication method not supported: '{}'", method);
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        LoginAuthenticationToken authRequest =
                new LoginAuthenticationToken(
                        account != null ? account : "",
                        key != null ? key : "",
                        loginWay != null ? loginWay : ""
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
            // ignore
        }
        log.trace("Authentication success, info: {}", authentication);

        // 若验证成功，将结果存入security上下文
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
        return request.getParameter(ACCOUNT_PARAM);
    }
    /**
     * 从请求中获取密钥
     */
    @Nullable
    protected String obtainKey(HttpServletRequest request)
    {
        return request.getParameter(KEY_PARAM);
    }

    protected void setDetails(HttpServletRequest request, LoginAuthenticationToken authRequest)
    {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
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