package cn.maplerabbit.rlg.common.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Spring相关配置项
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = SpringProperties.PREFIX)
public class SpringProperties
{
    public static final String PREFIX = "spring";

    private SecurityProperties security;

    private MailProperties mail;

    /**
     * spring-security相关配置
     */
    @Getter
    @Setter
    public static class SecurityProperties
    {
        /**
         * url白名单
         */
        private List<String> urlAllowlist;
    }

    /**
     * spring-mail相关配置
     */
    @Getter
    @Setter
    public static class MailProperties
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
    }
}