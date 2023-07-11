package cn.maplerabbit.rlg.util.impl;

import cn.maplerabbit.rlg.common.exception.TokenError;
import cn.maplerabbit.rlg.common.property.RlgProperties;
import cn.maplerabbit.rlg.common.util.ITokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class TokenUtilImpl implements ITokenUtil
{
    @Autowired
    private RlgProperties rlgProperties;

    public TokenUtilImpl() {log.debug("TokenUtilImpl()...");}

    public String generate(Map<String, Object> claims, Date timeout, String algorithm, String type, String secretKey)
    {
        if (timeout.before(new Date())) throw new TokenError("Timeout setting is invalid");
        if (!StringUtils.hasText(algorithm)) throw new TokenError("Token algorithm cannot be empty");
        if (!StringUtils.hasText(type)) throw new TokenError("Token type cannot be empty");
        if (!StringUtils.hasText(secretKey)) throw new TokenError("Token secretKey cannot be empty");

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
                        secretKey
                )
                .compact(); // 获取JWT
    }

    public String generate(Map<String, Object> claims, Date timeout)
    {
        return generate(
                claims,
                timeout,
                rlgProperties.getToken().getAlgorithm(),
                rlgProperties.getToken().getType(),
                rlgProperties.getToken().getSecretKey()
        );
    }

    public String generate(Map<String, Object> claims)
    {
        return generate(
                claims,
                timeout(rlgProperties.getToken().getUsableMinutes())
        );
    }
}