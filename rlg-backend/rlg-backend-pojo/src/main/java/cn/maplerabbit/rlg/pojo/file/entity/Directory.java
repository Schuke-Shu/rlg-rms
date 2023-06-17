package cn.maplerabbit.rlg.pojo.file.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String userUuid;
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
    private Integer isDirectory;
    /**
     * 是否在回收站中，1:是，0:否
     */
    private Integer isDeleted;
    /**
     * 进入回收站时间
     */
    private LocalDateTime deleteTime;
    /**
     * 是否隐藏
     */
    private Integer isHidden;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreated;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Directory{");
        sb.append("id=").append(id);
        sb.append(", fileUuid='").append(fileUuid).append('\'');
        sb.append(", userUuid='").append(userUuid).append('\'');
        sb.append(", filename='").append(filename).append('\'');
        sb.append(", deep=").append(deep);
        sb.append(", parentId=").append(parentId);
        sb.append(", isDirectory=").append(isDirectory);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append(", isHidden=").append(isHidden);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append('}');
        return sb.toString();
    }
}