package cn.maplerabbit.rlg.pojo.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("用户登录信息")
public class UserLoginVO implements Serializable
{
    @ApiModelProperty("uuid")
    private String uuid;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("头像地址")
    private String avatarUrl;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("电子邮箱")
    private String email;
    @ApiModelProperty("是否启用，1启用，0禁用")
    private Integer enable;
    @ApiModelProperty("权限集合")
    private List<String> permissions;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("uuid='").append(uuid).append('\'')
                .append(", username='").append(username).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", avatarUrl='").append(avatarUrl).append('\'')
                .append(", phone='").append(phone).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", enable=").append(enable)
                .append(", permissions=").append(permissions)
                .append('}')
                .toString();
    }
}