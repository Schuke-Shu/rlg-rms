package cn.maplerabbit.rlg.module.user.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;

public class UserNotFoundException
        extends UserException
{
    public UserNotFoundException(ServiceCode serviceCode, String msg) {super(serviceCode, msg);}
}