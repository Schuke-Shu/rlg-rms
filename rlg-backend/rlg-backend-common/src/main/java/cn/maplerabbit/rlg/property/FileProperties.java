package cn.maplerabbit.rlg.property;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Getter
@Setter
@Accessors(chain = true)
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.file")
public class FileProperties
{
    /**
     * 项目根目录路径
     */
    private String root;

    /**
     * 项目根目录
     */
    private File rootDir;
    /**
     * 静态资源目录
     */
    private File staticDir;
    /**
     * 仓库目录
     */
    private File storeDir;

    /**
     * 是否初始化过
     */
    private boolean initialized = false;

    public FileProperties() {log.debug("FileProperties()...");}
}