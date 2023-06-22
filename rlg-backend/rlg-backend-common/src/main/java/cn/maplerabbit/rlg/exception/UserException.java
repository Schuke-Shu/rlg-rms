package cn.maplerabbit.rlg.exception;

import cn.maplerabbit.rlg.entity.ServiceCode;

/**
 * 用户模块异常
 */
public class UserException
        extends ServiceException
{
    public UserException(ServiceCode code, String message)
    {
        super(code, message);
    }
}