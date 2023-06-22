package cn.maplerabbit.rlg.property;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 文件系统相关配置属性类
 */
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
     * 存储文件对象
     */
    private Map<String, File> map = new HashMap<>();

    public FileProperties() {log.debug("FileProperties()...");}

    public void put(String dirname, File dir) {map.put(dirname, dir);}

    public File get(String dirname)
    {
        return Objects.requireNonNull(map.get(dirname));
    }
}