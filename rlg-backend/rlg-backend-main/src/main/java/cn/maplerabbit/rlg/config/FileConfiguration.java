package cn.maplerabbit.rlg.config;

import cn.maplerabbit.rlg.constpak.DirectoryNameConst;
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
public class FileConfiguration
        implements InitializingBean,
                   DirectoryNameConst
{
    @Autowired
    private FileProperties fileProperties;

    public FileConfiguration() {log.debug("DirectoryConfiguration()...");}

    /**
     * 创建项目文件夹
     */
    @Override
    public void afterPropertiesSet()
            throws
            Exception
    {
        String root = fileProperties.getRoot();

        File rootDir = new File(root);
        File staticDir = new File(root, STATIC_DIR);
        File storeDir = new File(root, STORE_DIR);

        fileProperties.put("root", rootDir);
        fileProperties.put("static", staticDir);
        fileProperties.put("store", storeDir);

        mkdirs(rootDir);
        mkdirs(storeDir);
        mkdirs(staticDir);
    }

    private void mkdirs(File file)
    {
        if (!file.exists())
        {
            log.trace("create file【{}】", file.getAbsolutePath());
            file.mkdirs();
        }
    }
}