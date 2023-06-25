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
public class Directory implements Serializable
{
    private Long id;
    /**
     * 对应文件uuid
     */
    private String fileUuid;
    /**
     * 所属用户uuid
     */
    private Long userId;
    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件层级
     */
    private Integer deep;
    /**
     * 父目录id
     */
    private Long parentId;
    /**
     * 是否为目录，1:是，0:否
     */
    private Integer directory;
    /**
     * 文件大小（冗余）
     */
    private Long fileSize;
    /**
     * 是否在回收站中，1:是，0:否
     */
    private Integer deleted;
    /**
     * 进入回收站时间
     */
    private LocalDateTime deleteTime;
    /**
     * 是否隐藏
     */
    private Integer hidden;
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
                .append(", fileUuid='").append(fileUuid).append('\'')
                .append(", userId=").append(userId)
                .append(", filename='").append(filename).append('\'')
                .append(", deep=").append(deep)
                .append(", parentId=").append(parentId)
                .append(", directory=").append(directory)
                .append(", fileSize=").append(fileSize)
                .append(", deleted=").append(deleted)
                .append(", deleteTime=").append(deleteTime)
                .append(", hidden=").append(hidden)
                .append(", createTime=").append(createTime)
                .append(", modifiedTime=").append(modifiedTime)
                .append('}')
                .toString();
    }
}