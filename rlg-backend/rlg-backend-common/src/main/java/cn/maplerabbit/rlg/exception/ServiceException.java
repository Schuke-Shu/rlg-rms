package cn.maplerabbit.rlg.exception;

import cn.maplerabbit.rlg.entity.ServiceCode;

/**
 * 服务异常
 */
public class ServiceException extends RuntimeException
{
    private final ServiceCode code;

    public ServiceException(ServiceCode code, String message)
    {
        super(message);
        this.code = code;
    }

    public ServiceCode getCode() {
        return code;
    }
}