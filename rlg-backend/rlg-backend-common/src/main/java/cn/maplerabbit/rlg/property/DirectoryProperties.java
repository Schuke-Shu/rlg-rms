package cn.maplerabbit.rlg.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 文件系统相关配置属性类
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.directory")
public class DirectoryProperties implements InitializingBean
{
    /**
     * 项目根目录路径
     */
    private String rootDir;
    /**
     * 静态资源路径
     */
    private String staticDir;
    /**
     * 用户文件仓库路径
     */
    private String storeDir;

    /**
     * 项目根目录文件夹
     */
    private File rootDir$;
    /**
     * 静态资源文件夹
     */
    private File staticDir$;
    /**
     * 用户文件仓库文件夹
     */
    private File storeDir$;

    public DirectoryProperties() {log.debug("FileProperties()...");}


    @Override
    public void afterPropertiesSet()
            throws Exception
    {
        rootDir$ = new File(rootDir);
        staticDir$ = new File(staticDir);
        storeDir$ = new File(storeDir);
    }
}