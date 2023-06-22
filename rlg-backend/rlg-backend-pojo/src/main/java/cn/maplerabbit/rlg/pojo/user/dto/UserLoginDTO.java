package cn.maplerabbit.rlg.pojo.user.dto;

import cn.maplerabbit.rlg.constpak.ValidationMessageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("用户登录DTO")
public class UserLoginDTO
        implements ValidationMessageConst
{
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = USER_USERNAME_NOTBLANK)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = USER_PASSWORD_NOTBLANK)
    private String password;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("username='").append(username).append('\'')
                .append(", password='").append(password).append('\'')
                .append('}')
                .toString();
    }
}