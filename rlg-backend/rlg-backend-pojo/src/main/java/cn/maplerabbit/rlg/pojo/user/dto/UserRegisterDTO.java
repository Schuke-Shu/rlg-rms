package cn.maplerabbit.rlg.pojo.user.dto;

import cn.maplerabbit.rlg.constpak.ValidationMessageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("用户注册DTO")
public class UserRegisterDTO
        implements Serializable,
                   ValidationMessageConst
{
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = USER_USERNAME_NOTBLANK)
    @Size(max = 9, message = USER_USERNAME_SIZE)
    @Pattern(regexp = "^[\\u4E00-\\u9FFF\\w]+$", message = USER_USERNAME_PATTERN)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = USER_PASSWORD_NOTBLANK)
    @Size(min = 8, max = 16, message = USER_PASSWORD_SIZE)
    @Pattern(regexp = "^[\\w@#$%*.\\-\\\\]+$", message = USER_PASSWORD_PATTERN ,flags = {Pattern.Flag.MULTILINE, Pattern.Flag.UNIX_LINES})
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