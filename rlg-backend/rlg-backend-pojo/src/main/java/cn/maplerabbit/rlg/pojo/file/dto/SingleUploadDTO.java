package cn.maplerabbit.rlg.pojo.file.dto;

import cn.maplerabbit.rlg.common.constpak.ValidationMessageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("单文件上传DTO")
public class SingleUploadDTO
        implements Serializable
{
    @ApiModelProperty("文件")
    @NotNull
    private MultipartFile file;
    @ApiModelProperty("父目录id")
    private Long parentId;
    @ApiModelProperty("是否隐藏")
    private Integer hidden;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("file=").append(file)
                .append(", parentId=").append(parentId)
                .append(", hidden=").append(hidden)
                .append('}')
                .toString();
    }
}