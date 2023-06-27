package cn.maplerabbit.rlg.module.log.service;

import cn.maplerabbit.rlg.pojo.log.entity.UserLoginLog;

/**
 * 用户登录日志服务类
 */
public interface IUserLoginLogService
{
    /**
     * 添加用户登录日志
     * @param userLoginLog 用户登录日志
     * @return 添加数据数量
     */
    void addLog(UserLoginLog userLoginLog);
}