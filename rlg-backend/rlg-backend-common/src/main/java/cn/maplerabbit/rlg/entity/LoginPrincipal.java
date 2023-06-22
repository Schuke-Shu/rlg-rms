package cn.maplerabbit.rlg.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * security当事人类
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class LoginPrincipal implements Serializable {

    /**
     * 数据id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * ip
     */
    private String ip;
    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("id=").append(id)
                .append(", username='").append(username).append('\'')
                .append(", phone='").append(phone).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", ip='").append(ip).append('\'')
                .append('}')
                .toString();
    }
}
