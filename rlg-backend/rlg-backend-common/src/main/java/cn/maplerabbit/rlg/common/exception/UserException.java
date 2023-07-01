package cn.maplerabbit.rlg.common.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;

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