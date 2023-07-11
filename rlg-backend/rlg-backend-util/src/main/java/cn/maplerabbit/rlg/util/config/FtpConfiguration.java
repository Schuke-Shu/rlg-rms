package cn.maplerabbit.rlg.util.config;

import cn.maplerabbit.rlg.common.property.RlgProperties;
import cn.maplerabbit.rlg.util.ftp.FtpClientFactory;
import cn.maplerabbit.rlg.util.ftp.FtpClientPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FtpConfiguration
{
    @Autowired
    private RlgProperties rlgProperties;

    public FtpConfiguration() {log.debug("FtpConfiguration()...");}

    @Bean
    public FtpClientPool ftpClientPool() throws Exception
    {
        return new FtpClientPool(
                rlgProperties
                        .getFile()
                        .getFtp()
                        .getPool(),
                new FtpClientFactory(
                        rlgProperties
                                .getFile()
                                .getFtp()
                )
        );
    }
}