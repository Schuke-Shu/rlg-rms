package cn.maplerabbit.rlg.exception;

import cn.maplerabbit.rlg.enumpak.ServiceCode;

/**
 * 自定义运行时异常
 */
public class RlgException extends RuntimeException
{
    private final ServiceCode code;

    public RlgException(ServiceCode code, String message)
    {
        super(message);
        this.code = code;
    }

    public ServiceCode code() {
        return code;
    }
}