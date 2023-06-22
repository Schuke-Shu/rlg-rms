package cn.maplerabbit.rlg.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.util.Properties;

/**
 * Validation配置类
 */
@Slf4j
@Configuration
public class ValidationConfiguration
{
    @Autowired
    private ResourceBundleMessageSource messageSource;

    public ValidationConfiguration() {
        log.debug("ValidationConfiguration()...");
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

        // 设置国际化信息源文件
        bean.setValidationMessageSource(messageSource);

        // 使用HibernateValidator校验器
        bean.setProviderClass(HibernateValidator.class);

        // 配置快速异常返回
        Properties properties = new Properties();
        properties.setProperty("hibernate.validator.fail_fast", "true");
        bean.setValidationProperties(properties);

        // 加载配置
        bean.afterPropertiesSet();
        return bean.getValidator();
    }

}
