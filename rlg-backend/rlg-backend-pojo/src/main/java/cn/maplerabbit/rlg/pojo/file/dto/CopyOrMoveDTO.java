package cn.maplerabbit.rlg.pojo.file.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("文件复制或移位DTO")
public class CopyOrMoveDTO
{
    @ApiModelProperty(value = "文件索引id", required = true)
    private Long dictionaryId;
    @ApiModelProperty(value = "目标父目录id", required = true)
    private Long targetParentId;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("dictionaryId=").append(dictionaryId)
                .append(", targetParentId=").append(targetParentId)
                .append('}')
                .toString();
    }
}