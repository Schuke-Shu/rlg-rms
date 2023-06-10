package cn.maplerabbit.rlg.pojo.file.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class directory implements Serializable
{
    private Long id;
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
    private Integer isDir;
    /**
     * 是否在回收站中，1:是，0:否
     */
    private Integer isDel;
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
    private LocalDateTime gmtCreate;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;
}