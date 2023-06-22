package cn.maplerabbit.rlg.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt相关配置属性类
 */
@Getter
@Setter
@Accessors(chain = true)
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.jwt")
public class JwtProperties
{
    /**
     * 解析token使用的key
     */
    private String secretKey;
    /**
     * token有效时长（单位分钟）
     */
    private int usableMinutes;

    public JwtProperties() {log.debug("JwtProperties()...");}
}