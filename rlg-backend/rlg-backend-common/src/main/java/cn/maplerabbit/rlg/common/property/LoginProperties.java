package cn.maplerabbit.rlg.common.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 登录相关配置属性类
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.login")
public class LoginProperties
{
    /**
     * 存放登录方式的请求头名称
     */
    private String loginWayHeader;
    /**
     * 账户参数名（用户名、邮箱、手机号等的共同参数名）
     */
    private String paramAccount;
    /**
     * 密钥参数名（密码、验证码等的共同参数名）
     */
    private String paramKey;

    public LoginProperties() {log.debug("LoginProperties()...");}
}