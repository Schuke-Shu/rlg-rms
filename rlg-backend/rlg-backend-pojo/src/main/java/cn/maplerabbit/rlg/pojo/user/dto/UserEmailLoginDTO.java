package cn.maplerabbit.rlg.pojo.user.dto;

import cn.maplerabbit.rlg.constpak.ValidationMessageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("用户邮箱登录DTO")
public class UserEmailLoginDTO
        implements Serializable,
                   ValidationMessageConst
{
    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = USER_EMAIL)
    @Email(message = USER_EMAIL)
    private String email;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = VALIDATION_CODE_NOTBLANK)
    private String code;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("email='").append(email).append('\'')
                .append(", code='").append(code).append('\'')
                .append('}')
                .toString();
    }
}