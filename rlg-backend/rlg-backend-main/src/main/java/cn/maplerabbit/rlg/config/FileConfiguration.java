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
public class FileConfiguration implements InitializingBean
{
    @Autowired
    private FileProperties properties;

    /**
     * 静态资源文件夹名称
     */
    private static final String STATIC_DIR = "static";
    /**
     * 仓库文件夹名称
     */
    private static final String STORE_DIR = "store";

    public FileConfiguration() {log.debug("DirectoryConfiguration()...");}

    /**
     * 创建项目文件夹
     */
    @Override
    public void afterPropertiesSet() throws Exception
    {
        String root = properties.getRoot();

        File rootDir = new File(root);
        File storeDir = new File(root, STORE_DIR);
        File staticDir = new File(root, STATIC_DIR);

        properties.setRootDir(rootDir);
        properties.setRootDir(storeDir);
        properties.setRootDir(staticDir);

        mkdirs(rootDir);
        mkdirs(storeDir);
        mkdirs(staticDir);
    }

    private void mkdirs(File file)
    {
        if (!file.exists())
            file.mkdirs();
    }
}