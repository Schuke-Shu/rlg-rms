package cn.maplerabbit.rlg.config;

import cn.maplerabbit.rlg.mybatis.InsertUpdateTimeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

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

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    // 配置Mybatis拦截器
    @PostConstruct
    public void addInterceptor()
    {
        InsertUpdateTimeInterceptor interceptor = new InsertUpdateTimeInterceptor();

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList)
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
    }
}