package cn.maplerabbit.rlg.pojo.user.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录信息
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class UserLoginVO implements Serializable
{
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * id
     */
    private String password;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 是否启用，1启用，0禁用
     */
    private Integer enable;
    /**
     * 权限集合
     */
    private List<String> permissions;

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("id=").append(id)
                .append(", username='").append(username).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", phone='").append(phone).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", enable=").append(enable)
                .append(", permissions=").append(permissions)
                .append('}')
                .toString();
    }
}