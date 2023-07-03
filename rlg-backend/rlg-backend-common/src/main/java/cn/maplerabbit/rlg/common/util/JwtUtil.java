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
    /**
     * header key - alg
     */
    private static final String JWT_HEADER_PARAM_ALG = "alg";
    /**
     * header key - typ
     */
    private static final String JWT_HEADER_PARAM_TYP = "typ";

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
    public String generate(Map<String, Object> claims, int usableTime, String algorithm, String type)
    {
        if (!StringUtils.hasText(jwtProperties.getAlgorithm())) throw new JwtError("未配置jwt签名算法！");
        if (!StringUtils.hasText(jwtProperties.getType())) throw new JwtError("未配置jwt类型！");
        if (usableTime <= 0)
        {
            log.warn("设置的jwt有效时间无意义，修改为配置文件中配置的有效时间");
            usableTime = jwtProperties.getUsableMinutes();
        }
        if (usableTime <= 0) throw new JwtError("配置的jwt有效时间无意义！");

        return Jwts.builder() // 获取JwtBuilder，用于构建JWT
                // 配置Header
                .setHeaderParam(JWT_HEADER_PARAM_ALG, algorithm)
                .setHeaderParam(JWT_HEADER_PARAM_TYP, type)
                // 配置payload（存入数据）
                .setClaims(claims)
                // 配置Signature
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() +
                                        MILLISECONDS.convert(usableTime, TimeUnit.MINUTES)
                        )
                ) // JWT过期时间
                .signWith(
                        SignatureAlgorithm.forName(
                                algorithm
                        ),
                        type
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
    public String generate(Map<String, Object> claims, int usableTime)
    {
        return generate(claims, usableTime, jwtProperties.getAlgorithm(), jwtProperties.getType());
    }

    /**
     * 生成jwt，使用配置文件中的有效时间、签名算法、jwt类型
     *
     * @param claims 要存储的数据
     * @return JWT字符串
     */
    public String generate(Map<String, Object> claims)
    {
        return generate(claims, jwtProperties.getUsableMinutes(), jwtProperties.getAlgorithm(), jwtProperties.getType());
    }
}