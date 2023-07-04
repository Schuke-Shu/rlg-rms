package cn.maplerabbit.rlg.pojo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录验证在过滤器中进行，该类用于制作文档
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)

@ApiModel("用户名登录DTO")
public class UserLoginDTO
        implements Serializable
{
    @ApiModelProperty(value = "账户", required = true)
    private String account;

    @ApiModelProperty(value = "密钥", required = true)
    private String key;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("account='").append(account).append('\'')
                .append(", key='").append(key).append('\'')
                .append('}')
                .toString();
    }
}