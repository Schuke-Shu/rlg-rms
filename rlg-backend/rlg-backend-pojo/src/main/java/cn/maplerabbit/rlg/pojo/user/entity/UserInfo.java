package cn.maplerabbit.rlg.pojo.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserInfo implements Serializable
{
    private String userUuid;
    /**
     * 头像URL
     */
    private String avatar;
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
     * 最后登录IP地址
     */
    private String lastLoginIp;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 注册时间
     */
    private LocalDateTime registered;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;
}