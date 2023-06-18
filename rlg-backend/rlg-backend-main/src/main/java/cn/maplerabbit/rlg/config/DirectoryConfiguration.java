package cn.maplerabbit.rlg.config;

import cn.maplerabbit.rlg.property.FileProperties;
import cn.maplerabbit.rlg.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 文件系统配置
 */
@Slf4j
@Configuration
public class DirectoryConfiguration implements InitializingBean
{
    @Autowired
    private FileProperties properties;

    private static final String STATIC_DIR = "/static";

    public DirectoryConfiguration() {log.debug("DirectoryConfiguration()...");}

    @Override
    public void afterPropertiesSet() throws Exception
    {
        log.debug("entry DirectoryConfiguration afterPropertiesSet()...");

        mkdirs(properties.getStore());
        mkdirs(properties.getRoot() + STATIC_DIR);
    }

    private void mkdirs(String path)
    {
        if (FileUtil.mkdirs(path))
            log.debug("创建文件目录：{}", path);
        else
            log.warn("创建文件目录失败：{}", path);
    }
}