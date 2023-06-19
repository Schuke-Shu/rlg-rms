package cn.maplerabbit.rlg.config;

import cn.maplerabbit.rlg.property.FileProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 静态资源文件夹名称
     */
    private static final String STATIC_DIR = "/static";

    public DirectoryConfiguration() {log.debug("DirectoryConfiguration()...");}

    /**
     * 在所有bean初始化完成后执行
     */
    @Override
    public void afterPropertiesSet() throws Exception
    {
        log.debug("DirectoryConfiguration afterPropertiesSet()...");

        mkdirs(properties.getStore());
        mkdirs(properties.getRoot() + STATIC_DIR);
    }

    private void mkdirs(String path)
    {
        File file = new File(path);
        if (file.exists())
        {
            log.debug("目录[{}]已存在", path);
            return;
        }

        if (file.mkdirs())
            log.debug("创建目录：{}", path);
        else
            log.warn("创建目录失败：{}", path);
    }
}