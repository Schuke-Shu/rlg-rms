package cn.maplerabbit.rlg.pojo.log.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    public String toString()
    {
        return new StringJoiner(", ", UserLoginLog.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("username='" + username + "'")
                .add("ip='" + ip + "'")
                .add("engine='" + engine + "'")
                .add("time=" + time)
                .add("createTime=" + createTime)
                .add("modifiedTime=" + modifiedTime)
                .toString();
    }
}