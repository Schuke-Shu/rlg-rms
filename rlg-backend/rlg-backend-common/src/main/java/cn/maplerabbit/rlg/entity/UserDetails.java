package cn.maplerabbit.rlg.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * security用户信息类
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class UserDetails extends User implements Serializable
{
    private final Long id;
    /**
     * 手机号
     */
    private final String phone;
    /**
     * 邮箱
     */
    private final String email;
    private final String ip;

    public UserDetails(
            Long id,
            String username,
            String password,
            String phone,
            String email,
            String ip,
            boolean enabled,
            Collection<? extends GrantedAuthority> authorities
    )
    {
        super(username, password, enabled, true, true, true, authorities);
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.ip = ip;
    }

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("id=").append(id)
                .append(", phone='").append(phone).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", ip='").append(ip).append('\'')
                .append('}')
                .toString();
    }
}
