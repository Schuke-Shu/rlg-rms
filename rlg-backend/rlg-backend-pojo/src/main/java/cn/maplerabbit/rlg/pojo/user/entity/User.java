package cn.maplerabbit.rlg.pojo.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable
{
    private String uuid;
    /**
     * 昵称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像URL
     */
    private String avatarUuid;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 性别，1:男，0:女
     */
    private Integer gender;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 生日
     */
    private LocalDate birth;
    /**
     * 个人简介
     */
    private String description;
    /**
     * 是否启用，1:启用，0:禁用
     */
    private Integer isEnable;
    /**
     * 最后登录IP地址（冗余）
     */
    private String lastLoginIp;
    /**
     * 最后登录时间（冗余）
     */
    private LocalDateTime lastLoginTime;
    /**
     * 注册时间
     */
    private LocalDateTime gmtRegistered;
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
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", avatarUuid='").append(avatarUuid).append('\'');
        sb.append(", realName='").append(realName).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", birth=").append(birth);
        sb.append(", description='").append(description).append('\'');
        sb.append(", isEnable=").append(isEnable);
        sb.append(", lastLoginIp='").append(lastLoginIp).append('\'');
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", gmtRegistered=").append(gmtRegistered);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append('}');
        return sb.toString();
    }
}