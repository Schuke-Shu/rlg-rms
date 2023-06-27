package cn.maplerabbit.rlg.exception;

import cn.maplerabbit.rlg.enumpak.ServiceCode;

public class RoleException
        extends ServiceException
{
    public RoleException(ServiceCode code, String message)
    {
        super(code, message);
    }
}