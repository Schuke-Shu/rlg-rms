package cn.maplerabbit.rlg.common.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证码相关配置
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.code")
public class CodeProperties
{
    /**
     * 有效时间（单位：分钟）
     */
    private int usableTime;

    public CodeProperties() {log.debug("CodeProperties()...");}
}