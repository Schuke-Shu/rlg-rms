package cn.maplerabbit.rlg.pojo.file.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

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
    private Long userId;
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
     * 关联到此文件的记录数
     */
    private Integer associationCount;
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
        return new StringJoiner(", ", File.class.getSimpleName() + "[", "]")
                .add("uuid='" + uuid + "'")
                .add("userId=" + userId)
                .add("suffix='" + suffix + "'")
                .add("type='" + type + "'")
                .add("size=" + size)
                .add("associationCount=" + associationCount)
                .add("uploadTime=" + uploadTime)
                .add("createTime=" + createTime)
                .add("modifiedTime=" + modifiedTime)
                .toString();
    }
}