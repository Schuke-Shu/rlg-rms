package cn.maplerabbit.rlg.common.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * token相关配置属性类
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.token")
public class TokenProperties
{
    /**
     * token签名算法
     */
    private String algorithm;
    /**
     * token类型
     */
    private String type;
    /**
     * 解析和生成token使用的key
     */
    private String secretKey;
    /**
     * token有效时长（单位分钟）
     */
    private int usableMinutes;
    /**
     * 长度下限
     */
    private int minLength;
    /**
     * 存放token的请求头的名称
     */
    private String header;
    /**
     * token可刷新临期时间（单位：分钟）
     */
    private int refreshTime;

    public TokenProperties() {log.debug("TokenProperties()...");}
}