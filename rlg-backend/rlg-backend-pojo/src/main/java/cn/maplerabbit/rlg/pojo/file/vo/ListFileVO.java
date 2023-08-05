package cn.maplerabbit.rlg.pojo.file.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 查询文件列表VO
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class ListFileVO implements Serializable
{
    @ApiModelProperty("文件索引id")
    private Long id;
    @ApiModelProperty("对应文件uuid")
    private String fileUuid;
    @ApiModelProperty("文件名称")
    private String filename;
    @ApiModelProperty("文件路径")
    private String path;
    @ApiModelProperty("是否为目录，1:是，0:否")
    private Integer directory;
    @ApiModelProperty("文件大小（冗余）")
    private Long fileSize;
    @ApiModelProperty("是否隐藏")
    private Integer hidden;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("id=").append(id)
                .append(", fileUuid='").append(fileUuid).append('\'')
                .append(", filename='").append(filename).append('\'')
                .append(", path='").append(path).append('\'')
                .append(", directory=").append(directory)
                .append(", fileSize=").append(fileSize)
                .append(", hidden=").append(hidden)
                .append('}')
                .toString();
    }
}