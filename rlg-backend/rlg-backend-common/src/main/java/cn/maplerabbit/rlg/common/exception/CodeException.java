package cn.maplerabbit.rlg.common.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;

/**
 * 验证码异常
 */
public class CodeException extends ServiceException
{
    public CodeException(ServiceCode code, String message)
    {
        super(code, message);
    }
}