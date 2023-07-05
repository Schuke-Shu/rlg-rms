package cn.maplerabbit.rlg.module.user.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;

public class RoleException
        extends UserException
{
    public RoleException(ServiceCode code, String message)
    {
        super(code, message);
    }
}