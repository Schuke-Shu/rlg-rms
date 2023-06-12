package cn.maplerabbit.rlg.module.file.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 关联到此文件的记录数
     */
    private Integer associationCount;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreated;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;
}