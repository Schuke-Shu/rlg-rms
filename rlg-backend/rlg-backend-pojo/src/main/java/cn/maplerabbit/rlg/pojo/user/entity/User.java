package cn.maplerabbit.rlg.pojo.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class User implements Serializable
{
    private String uuid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;
}