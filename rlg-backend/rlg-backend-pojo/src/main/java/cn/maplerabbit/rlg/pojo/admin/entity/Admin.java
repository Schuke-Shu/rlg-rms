package cn.maplerabbit.rlg.pojo.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Admin implements Serializable
{
    private Long id;
    /**
     * 创建者id
     */
    private String creatorId;
    /**
     * 昵称
     */
    private String adminName;
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
    public String toString()
    {
        return new StringJoiner(", ", Admin.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("creatorId='" + creatorId + "'")
                .add("adminName='" + adminName + "'")
                .add("password='" + password + "'")
                .add("avatarUrl='" + avatarUrl + "'")
                .add("realName='" + realName + "'")
                .add("gender=" + gender)
                .add("phone='" + phone + "'")
                .add("email='" + email + "'")
                .add("birth=" + birth)
                .add("description='" + description + "'")
                .add("isEnable=" + isEnable)
                .add("signUpTime=" + signUpTime)
                .add("createTime=" + createTime)
                .add("modifiedTime=" + modifiedTime)
                .toString();
    }
}