package cn.maplerabbit.rlg.filter;

import cn.maplerabbit.rlg.common.constpak.LoginPrincipalConst;
import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.TokenError;
import cn.maplerabbit.rlg.common.property.TokenProperties;
import cn.maplerabbit.rlg.common.security.LoginPrincipal;
import cn.maplerabbit.rlg.common.util.IpUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.maplerabbit.rlg.common.util.FilterError.error;


/**
 * JWT过滤解析器
 */
@Slf4j
public class TokenAuthorizationFilter
        extends OncePerRequestFilter
        implements LoginPrincipalConst
{
    private final TokenProperties tokenProperties;

    public TokenAuthorizationFilter(TokenProperties jwtProperties)
    {
        this.tokenProperties = jwtProperties;
        log.debug("TokenAuthorizationFilter()...");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws
            ServletException,
            IOException
    {
        log.debug("Access TokenAuthorizationFilter");

        String secretKey = tokenProperties.getSecretKey();

        // 判断密钥是否为空
        if (!StringUtils.hasText(secretKey))
            throw new TokenError("JWT SecretKey is blank...");

        // 清空SecurityContext，强制所有请求都必须携带JWT
        SecurityContextHolder.clearContext();

        // 从请求头获取token
        String token = request.getHeader(tokenProperties.getHeader());

        log.trace("token: {}", token);
        // 检查token是否有效
        if (!StringUtils.hasText(token) || token.length() < tokenProperties.getMinLength())
        {
            // token无效，放行
            log.debug("Token is invalid, to next...");
            filterChain.doFilter(request, response);
            return;
        }

        // token有效，准备解析
        log.debug("Parsing token...");
        Claims claims = null;
        try
        {
            claims = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (ExpiredJwtException e)
        {
            // token过期
            log.debug("-- ExpiredJwtException, token过期");
            log.trace("token: {}", token);
            error(
                    response,
                    ErrorResult.fail(ServiceCode.ERR_JWT_EXPIRED, "您的登录信息已过期，请重新登录")
            );
            return;
        }
        catch (SignatureException e)
        {
            // 签名错误，token被篡改
            log.warn("-- SignatureException，ip: {}", request.getRemoteAddr());
            error(
                    response,
                    ErrorResult.fail(ServiceCode.ERR_JWT_SIGNATURE, "非法访问！")
            );
            return;
        }
        catch (MalformedJwtException e)
        {
            // token格式不正确，token被篡改
            log.warn("-- MalformedJwtException，ip: {}", request.getRemoteAddr());
            error(
                    response,
                    ErrorResult.fail(ServiceCode.ERR_JWT_MALFORMED, "非法访问！")
            );
            return;
        }
        catch (Throwable e)
        {
            // 出现未处理异常
            log.error("-- Unhandled Exception: {}, msg: {}", e.getClass().getName(), e.getMessage());
            e.printStackTrace();
            error(
                    response,
                    ErrorResult.fail(ServiceCode.ERR_UNKNOWN, "服务器忙，请稍后再试")
            );
            return;
        }

        // 从Claims中获取数据
        String ip = claims.get(CLAIMS_KEY_IP, String.class);
        String uuid = claims.get(CLAIMS_KEY_UUID, String.class);
        String username = claims.get(CLAIMS_KEY_USERNAME, String.class);
        String phone = claims.get(CLAIMS_KEY_PHONE, String.class);
        String email = claims.get(CLAIMS_KEY_EMAIL, String.class);
        String authoritiesJson = claims.get(CLAIMS_KEY_AUTHORITIES, String.class);

        if (!IpUtil.getIp(request).equals(ip))
        {
            log.debug("ip error, to next...");
            // 请求IP与token记录的IP不同时，不存储解析到的信息，直接放行，由之后的过滤器处理
            filterChain.doFilter(request, response);
        }
        else
        {
            // 创建Authentication对象，存入到SecurityContext中
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    // 封装Principal（当事人）对象
                    new LoginPrincipal(uuid, username, phone, email, ip),
                    // 凭证（这里不需要）
                    null,
                    // 解析json形式的权限集合
                    JSON.parseArray(authoritiesJson, SimpleGrantedAuthority.class)
            );

            log.debug("获取到Authentication对象并存入SecurityContext");
            log.trace("authentication: {}", authentication);
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            // 放行
            log.debug("Token parsing success, to next...");
            filterChain.doFilter(request, response);
        }
    }
}