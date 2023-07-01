package cn.maplerabbit.rlg.common.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;

public class RoleException
        extends ServiceException
{
    public RoleException(ServiceCode code, String message)
    {
        super(code, message);
    }
}