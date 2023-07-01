package cn.maplerabbit.rlg.filter;

import cn.maplerabbit.rlg.common.constpak.LoginPrincipalConst;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.TokenException;
import cn.maplerabbit.rlg.common.property.JwtProperties;
import cn.maplerabbit.rlg.common.entity.LoginPrincipal;
import cn.maplerabbit.rlg.common.util.IpUtil;
import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT过滤解析器
 */
@Slf4j
@Component
public class JwtAuthorizationFilter
        extends OncePerRequestFilter
        implements LoginPrincipalConst
{
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private HttpServletResponse response;

    public JwtAuthorizationFilter()
    {
        log.debug("JwtAuthorizationFilter()...");
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
        String secretKey = jwtProperties.getSecretKey();

        // 判断密钥是否为空
        if (!StringUtils.hasText(secretKey))
            throw new TokenException("JWT SecretKey is blank...");

        // 清空SecurityContext，强制所有请求都必须携带JWT
        SecurityContextHolder.clearContext();

        // 从请求头获取JWT
        String jwt = request.getHeader(jwtProperties.getHeader());

        log.trace("jwt: {}", jwt);
        // 检查JWT是否有效
        if (!StringUtils.hasText(jwt) || jwt.length() < jwtProperties.getMinLength())
        {
            // JWT无效，放行
            log.debug("JWT is blank, to next...");
            filterChain.doFilter(request, response);
            return;
        }

        // JWT有效，解析JWT
        log.debug("Parsing JWT...");
        Claims claims = null;
        try
        {
            claims = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        }
        catch (ExpiredJwtException e)
        {
            // JWT过期
            log.debug("-- ExpiredJwtException, jwt过期");
            log.trace("JWT: {}", jwt);
            error(ServiceCode.ERR_JWT_EXPIRED, "您的登录信息已过期，请重新登录");
            return;
        }
        catch (SignatureException e)
        {
            // 签名错误，JWT被篡改
            log.warn("-- SignatureException，ip: {}", request.getRemoteAddr());
            error(ServiceCode.ERR_JWT_SIGNATURE, "非法访问！");
            return;
        }
        catch (MalformedJwtException e)
        {
            // JWT格式不正确，JWT被篡改
            log.warn("-- MalformedJwtException，ip: {}", request.getRemoteAddr());
            error(ServiceCode.ERR_JWT_MALFORMED, "非法访问！");
            return;
        }
        catch (Throwable e)
        {
            // 出现未处理异常
            log.error("-- Unhandled Exception: {}", e.getClass().getName());
            e.printStackTrace();
            error(ServiceCode.ERR_UNKNOWN, "服务器忙，请稍后再试");
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
            // 请求IP与JWT记录的IP不同时，不存储解析到的信息，直接放行，由之后的过滤器处理
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
            log.debug("JWT parsing success, to next...");
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 向客户端返回异常信息
     */
    private void error(ServiceCode code, String msg)
    {
        response.setContentType("application/json;charset=utf-8");

        try
        {
            PrintWriter writer = response.getWriter();

            writer.println(
                    JSON.toJSONString(
                            ErrorResult.fail(code, msg)
                    )
            );

            writer.close();
        }
        catch (IOException e)
        {
            log.error("-- IOException, msg: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}