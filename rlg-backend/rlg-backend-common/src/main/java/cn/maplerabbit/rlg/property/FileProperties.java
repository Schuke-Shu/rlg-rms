package cn.maplerabbit.rlg.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.file")
public class FileProperties
{
    /**
     * 用户文件仓库目录
     */
    private String store;
    /**
     * 项目文件根目录
     */
    private String root;

    public FileProperties() {log.debug("FileProperties()...");}

    @Override
    public String toString()
    {
        return new StringBuilder("FileProperties{")
                .append("store='").append(store).append('\'')
                .append(", root='").append(root).append('\'')
                .append('}')
                .toString();
    }
}