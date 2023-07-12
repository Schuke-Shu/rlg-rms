package cn.maplerabbit.rlg.pojo.file.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class File implements Serializable
{
    private String uuid;
    /**
     * 文件上传者
     */
    private String userUuid;
    /**
     * 文件md5值
     */
    private String sha512;
    /**
     * 后缀名
     */
    private String suffix;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件上传时间
     */
    private LocalDateTime uploadTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime modifiedTime;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("uuid='").append(uuid).append('\'')
                .append(", userUuid='").append(userUuid).append('\'')
                .append(", sha512='").append(sha512).append('\'')
                .append(", suffix='").append(suffix).append('\'')
                .append(", type='").append(type).append('\'')
                .append(", size=").append(size)
                .append(", uploadTime=").append(uploadTime)
                .append(", createTime=").append(createTime)
                .append(", modifiedTime=").append(modifiedTime)
                .append('}')
                .toString();
    }
}