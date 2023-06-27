package cn.maplerabbit.rlg.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt相关配置属性类
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.jwt")
public class JwtProperties
{
    /**
     * jwt签名算法
     */
    private String algorithm;
    /**
     * jwt类型
     */
    private String type;
    /**
     * 解析token使用的key
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
     * 存放jwt的请求头的名称
     */
    private String header;

    public JwtProperties() {log.debug("JwtProperties()...");}
}