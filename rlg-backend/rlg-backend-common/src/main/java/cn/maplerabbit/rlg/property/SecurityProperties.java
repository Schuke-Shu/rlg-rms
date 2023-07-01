package cn.maplerabbit.rlg.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * spring security相关配置属性类
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "spring.security")
public class SecurityProperties
{
    /**
     * url白名单
     */
    private String[] urlAllowlist;

    public SecurityProperties() {log.debug("SecurityProperties()...");}
}