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
@ConfigurationProperties(prefix = "rlg.security")
public class SecurityProperties
{
    private String[] urlAllowList;

    public SecurityProperties() {log.debug("SecurityProperties()...");}
}