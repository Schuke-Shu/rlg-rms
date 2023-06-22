package cn.maplerabbit.rlg.pojo.user.dto;

import cn.maplerabbit.rlg.constpak.ValidationMessageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("用户注册DTO")
public class UserRegisterDTO
        implements ValidationMessageConst
{
    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank(message = USER_USERNAME_NOTBLANK)
    @Size(max = 9, message = USER_USERNAME_SIZE)
    @Pattern(regexp = "^[\\u4E00-\\u9FFF\\w]+$", message = USER_USERNAME_PATTERN)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = USER_PASSWORD_NOTBLANK)
    @Size(min = 8, max = 16, message = USER_PASSWORD_SIZE)
    @Pattern(regexp = "^[\\w@#$%*\\.\\-\\\\]+$", message = USER_PASSWORD_PATTERN)
    private String password;
}