package cn.maplerabbit.rlg.pojo.user.dto;

import cn.maplerabbit.rlg.common.constpak.ValidationMessageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("用户更新DTO")
public class UserUpdateDTO
        implements ValidationMessageConst
{
    @ApiModelProperty(value = "uuid", required = true)
    @NotNull
    private String uuid;
    @ApiModelProperty(value = "用户名", required = true)
    @Size(max = 9, message = USER_USERNAME_SIZE)
    @Pattern(regexp = "^[\\u4E00-\\u9FFFA-Za-z][\\u4E00-\\u9FFF\\w]+$", message = USER_USERNAME_PATTERN)
    private String username;
    @ApiModelProperty(value = "密码")
    @Size(min = 8, max = 16, message = USER_PASSWORD_SIZE)
    @Pattern(regexp = "^[\\w@#$%*.\\-\\\\]+$", message = USER_PASSWORD_PATTERN)
    private String password;
    @ApiModelProperty(value = "头像url")
    private String avatarUrl;
    @ApiModelProperty(value = "性别")
    private Integer gender;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "生日")
    private LocalDate birth;
    @ApiModelProperty(value = "个人简介")
    private String description;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("uuid='").append(uuid).append('\'')
                .append(", username='").append(username).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", avatarUrl='").append(avatarUrl).append('\'')
                .append(", gender=").append(gender)
                .append(", phone='").append(phone).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", birth=").append(birth)
                .append(", description='").append(description).append('\'')
                .append('}')
                .toString();
    }
}