package cn.maplerabbit.rlg.common.exception;

import cn.maplerabbit.rlg.common.entity.ErrorDetail;

public class FtpError
        extends ProgramError
{
    public FtpError(String msg)
    {
        super(msg);
    }
}