package cn.maplerabbit.rlg.common.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * spring-mail配置信息
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties
{
    /**
     * 系统邮箱host
     */
    private String host;
    /**
     * 系统邮箱的账户
     */
    private String username;
    /**
     * 系统邮箱授权码（不是密码）
     */
    private String password;

    public MailProperties() {log.debug("MailProperties()...");}
}