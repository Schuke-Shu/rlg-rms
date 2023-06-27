package cn.maplerabbit.rlg.module.log.service.impl;

import cn.maplerabbit.rlg.module.log.mapper.UserLoginLogMapper;
import cn.maplerabbit.rlg.module.log.service.IUserLoginLogService;
import cn.maplerabbit.rlg.pojo.log.entity.UserLoginLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录日志服务实现类
 */
@Slf4j
@Service
public class UserLoginLogServiceImpl implements IUserLoginLogService
{
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    public UserLoginLogServiceImpl() {log.debug("UserLoginLogServiceImpl()...");}

    @Override
    public void addLog(UserLoginLog userLoginLog)
    {
        if (
                userLoginLogMapper.save(userLoginLog) != 1
        )
            log.warn("Add user login log failed, data: {}", userLoginLog);
    }
}