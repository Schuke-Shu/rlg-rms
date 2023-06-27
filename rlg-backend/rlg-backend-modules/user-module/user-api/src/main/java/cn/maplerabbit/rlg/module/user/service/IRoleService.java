package cn.maplerabbit.rlg.module.user.service;

/**
 * 角色管理服务类
 */
public interface IRoleService
{
    /**
     * 普通用户角色
     */
    String ROLE_USER = "ROLE_USER";

    /**
     * 用于用户注册时绑定默认的普通角色
     * @param uuid 用户uuid
     */
    void bindDefaultRole(String uuid);
}