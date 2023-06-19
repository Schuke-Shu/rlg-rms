package cn.maplerabbit.rlg.pojo.log.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class UserLoginLog implements Serializable
{
    private Long id;
    private Long userId;
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
    private LocalDateTime time;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime modifiedTime;

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("id=").append(id)
                .append(", userId=").append(userId)
                .append(", username='").append(username).append('\'')
                .append(", ip='").append(ip).append('\'')
                .append(", engine='").append(engine).append('\'')
                .append(", time=").append(time)
                .append(", createTime=").append(createTime)
                .append(", modifiedTime=").append(modifiedTime)
                .append('}')
                .toString();
    }
}