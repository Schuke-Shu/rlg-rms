package cn.maplerabbit.rlg.common.util;

import cn.maplerabbit.rlg.common.exception.TokenError;
import cn.maplerabbit.rlg.common.property.TokenProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Component
public class TokenUtil
{
    @Autowired
    private TokenProperties tokenProperties;

    public TokenUtil() {log.debug("TokenUtil()...");}

    /**
     * 生成token，自定义有效时间、签名算法、token类型
     *
     * @param claims 要存储的数据
     * @param timeout 过期时间
     * @param algorithm 签名算法
     * @param type token类型
     * @return token字符串
     */
    public String generate(Map<String, Object> claims, Date timeout, String algorithm, String type)
    {
        if (!StringUtils.hasText(tokenProperties.getAlgorithm())) throw new TokenError("未配置token签名算法！");
        if (!StringUtils.hasText(tokenProperties.getType())) throw new TokenError("未配置token类型！");
        Date now = new Date();
        if (timeout.before(now))
        {
            log.warn("设置的token有效时间无意义，修改为配置文件中配置的有效时间");
            if (tokenProperties.getUsableMinutes() <= 0)
                throw new TokenError("配置的token有效时间无意义！");
            timeout = timeout(tokenProperties.getUsableMinutes());
        }

        log.trace("Generating token, data: {}", claims);
        return Jwts.builder() // 获取JwtBuilder，用于构建token
                // 配置Header
                .setHeaderParam(algorithm, algorithm)
                .setHeaderParam(type, type)
                // 配置payload（存入数据）
                .setClaims(claims)
                // 配置Signature
                .setExpiration(timeout) // token过期时间
                .signWith(
                        SignatureAlgorithm.forName(
                                algorithm
                        ),
                        tokenProperties.getSecretKey()
                )
                .compact(); // 获取JWT
    }

    /**
     * 生成token，自定义有效时间，使用配置文件中的签名算法、token类型
     *
     * @param claims 要存储的数据
     * @param timeout 过期时间
     * @return token字符串
     */
    public String generate(Map<String, Object> claims, Date timeout)
    {
        return generate(claims, timeout, tokenProperties.getAlgorithm(), tokenProperties.getType());
    }

    /**
     * 生成token，使用配置文件中的有效时间、签名算法、token类型
     *
     * @param claims 要存储的数据
     * @return token字符串
     */
    public String generate(Map<String, Object> claims)
    {
        return generate(
                claims,
                timeout(tokenProperties.getUsableMinutes())
        );
    }

    private Date timeout(int usableMinutes)
    {
        return new Date(
                System.currentTimeMillis() +
                        MILLISECONDS.convert(usableMinutes, TimeUnit.MINUTES)
        );
    }
}