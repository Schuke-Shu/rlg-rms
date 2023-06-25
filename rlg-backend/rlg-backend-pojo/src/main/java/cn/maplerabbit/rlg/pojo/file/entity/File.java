package cn.maplerabbit.rlg.pojo.file.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class File implements Serializable
{
    private Integer id;
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
     * 文件大小（冗余）
     */
    private Integer size;
    /**
     * 文件大小单位（冗余）
     */
    private String sizeUnit;
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
                .append("id=").append(id)
                .append(", uuid='").append(uuid).append('\'')
                .append(", userId=").append(userId)
                .append(", suffix='").append(suffix).append('\'')
                .append(", type='").append(type).append('\'')
                .append(", size=").append(size)
                .append(", sizeUnit='").append(sizeUnit).append('\'')
                .append(", uploadTime=").append(uploadTime)
                .append(", createTime=").append(createTime)
                .append(", modifiedTime=").append(modifiedTime)
                .append('}')
                .toString();
    }
}