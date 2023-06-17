package cn.maplerabbit.rlg.pojo.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserLoginLog implements Serializable
{
    private Long id;
    private String userUuid;
    /**
     * 用户名（冗余）
     */
    private String username;
    /**
     * 登录IP地址
     */
    private String ip;
    /**
     * 浏览器内核
     */
    private String engine;
    /**
     * 登录时间
     */
    private LocalDateTime gmtLogin;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreated;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("UserLoginLog{");
        sb.append("id=").append(id);
        sb.append(", userUuid='").append(userUuid).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", engine='").append(engine).append('\'');
        sb.append(", gmtLogin=").append(gmtLogin);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append('}');
        return sb.toString();
    }
}