package cn.maplerabbit.rlg.pojo.file.dto;

import cn.maplerabbit.rlg.common.constpak.ValidationMessageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("创建文件夹DTO")
public class MkdirDTO
        implements Serializable,
                   ValidationMessageConst
{
    @ApiModelProperty(value = "文件夹名", required = true)
    @NotNull(message = FILE_NAME_EMPTY)
    @Pattern(regexp = "^[\\u4E00-\\u9FFF\\w@#$%*.\\- ]+$", message = FILE_NAME_PATTERN)
    private String filename;
    @ApiModelProperty("父目录id")
    private Long parentId;
    @ApiModelProperty("是否隐藏")
    private Integer hidden;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("filename='").append(filename).append('\'')
                .append(", parentId=").append(parentId)
                .append(", hidden=").append(hidden)
                .append('}')
                .toString();
    }
}