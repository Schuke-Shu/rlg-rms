package cn.maplerabbit.rlg.module.file.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class File implements Serializable
{
    private String uuid;
    /**
     * 文件上传者
     */
    private String userUuid;
    /**
     * 后缀名
     */
    private String suffix;
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
    private LocalDateTime gmtCreated;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;
}