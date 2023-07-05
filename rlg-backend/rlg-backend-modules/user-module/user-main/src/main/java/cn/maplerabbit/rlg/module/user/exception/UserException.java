package cn.maplerabbit.rlg.module.user.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.ServiceException;

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