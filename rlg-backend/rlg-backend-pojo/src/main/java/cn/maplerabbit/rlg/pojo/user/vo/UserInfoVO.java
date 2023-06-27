package cn.maplerabbit.rlg.pojo.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("用户信息")
public class UserInfoVO implements Serializable
{
    @ApiModelProperty("uuid")
    private String uuid;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("头像Url")
    private String avatarUrl;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("uuid='").append(uuid).append('\'')
                .append(", username='").append(username).append('\'')
                .append(", avatarUrl='").append(avatarUrl).append('\'')
                .append('}')
                .toString();
    }
}