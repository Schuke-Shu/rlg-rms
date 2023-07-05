package cn.maplerabbit.rlg.module.user.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.ServiceException;

public class RoleException
        extends ServiceException
{
    public RoleException(ServiceCode code, String message)
    {
        super(code, message);
    }
}