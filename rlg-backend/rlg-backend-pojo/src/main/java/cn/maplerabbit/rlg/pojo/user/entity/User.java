package cn.maplerabbit.rlg.pojo.user.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class User implements Serializable
{
    private Long id;
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
    private String avatarUrl;
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
     * 注册时间
     */
    private LocalDateTime signUpTime;
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
                .append(", uuid='").append(uuid).append('\'')
                .append(", username='").append(username).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", avatarUrl='").append(avatarUrl).append('\'')
                .append(", realName='").append(realName).append('\'')
                .append(", gender=").append(gender)
                .append(", phone='").append(phone).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", birth=").append(birth)
                .append(", description='").append(description).append('\'')
                .append(", isEnable=").append(isEnable)
                .append(", signUpTime=").append(signUpTime)
                .append(", createTime=").append(createTime)
                .append(", modifiedTime=").append(modifiedTime)
                .append('}')
                .toString();
    }
}