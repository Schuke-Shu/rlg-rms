package cn.maplerabbit.rlg.common.util;

import cn.maplerabbit.rlg.common.exception.JwtError;
import cn.maplerabbit.rlg.common.property.JwtProperties;
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
public class JwtUtil
{
    @Autowired
    private JwtProperties jwtProperties;

    public JwtUtil() {log.debug("JwtUtil()...");}

    /**
     * 生成jwt，自定义有效时间、签名算法、jwt类型
     *
     * @param claims 要存储的数据
     * @param usableTime 有效时间
     * @param algorithm 签名算法
     * @param type jwt类型
     * @return JWT字符串
     */
    public String generate(Map<String, Object> claims, Date timeout, String algorithm, String type)
    {
        if (!StringUtils.hasText(jwtProperties.getAlgorithm())) throw new JwtError("未配置jwt签名算法！");
        if (!StringUtils.hasText(jwtProperties.getType())) throw new JwtError("未配置jwt类型！");
        Date now = new Date();
        if (timeout.before(now))
        {
            log.warn("设置的jwt有效时间无意义，修改为配置文件中配置的有效时间");
            if (jwtProperties.getUsableMinutes() <= 0)
                throw new JwtError("配置的jwt有效时间无意义！");
            timeout = timeout(jwtProperties.getUsableMinutes());
        }

        log.trace("Generating JWT, data: {}", claims);
        return Jwts.builder() // 获取JwtBuilder，用于构建JWT
                // 配置Header
                .setHeaderParam(algorithm, algorithm)
                .setHeaderParam(type, type)
                // 配置payload（存入数据）
                .setClaims(claims)
                // 配置Signature
                .setExpiration(timeout) // JWT过期时间
                .signWith(
                        SignatureAlgorithm.forName(
                                algorithm
                        ),
                        jwtProperties.getSecretKey()
                )
                .compact(); // 获取JWT
    }

    /**
     * 生成jwt，自定义有效时间，使用配置文件中的签名算法、jwt类型
     *
     * @param claims 要存储的数据
     * @param usableTime 有效时间
     * @return JWT字符串
     */
    public String generate(Map<String, Object> claims, Date timeout)
    {
        return generate(claims, timeout, jwtProperties.getAlgorithm(), jwtProperties.getType());
    }

    /**
     * 生成jwt，使用配置文件中的有效时间、签名算法、jwt类型
     *
     * @param claims 要存储的数据
     * @return JWT字符串
     */
    public String generate(Map<String, Object> claims)
    {
        return generate(
                claims,
                timeout(jwtProperties.getUsableMinutes())
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