package cn.maplerabbit.rlg.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置
 */
@Configuration
@MapperScan("cn.maplerabbit.rlg.module.**.mapper")
@Slf4j
public class MyBatisConfiguration
{
    public MyBatisConfiguration()
    {
        log.debug("MyBatisConfiguration()...");
    }
}