package cn.maplerabbit.rlg.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.maplerabbit.rlg.module")
@Slf4j
public class MyBatisConfiguration
{
    public MyBatisConfiguration()
    {
        log.trace("MyBatisConfiguration()...");
    }
}