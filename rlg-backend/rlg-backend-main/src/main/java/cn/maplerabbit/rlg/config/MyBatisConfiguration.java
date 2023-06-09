package cn.maplerabbit.rlg.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.maplerabbit.rlg.modules")
public class MyBatisConfiguration
{
}