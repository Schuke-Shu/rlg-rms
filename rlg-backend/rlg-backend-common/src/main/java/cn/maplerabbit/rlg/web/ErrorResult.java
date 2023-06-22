package cn.maplerabbit.rlg.web;

import cn.maplerabbit.rlg.entity.ServiceCode;
import cn.maplerabbit.rlg.exception.ServiceException;

import java.io.Serializable;

public class ErrorResult
        extends JsonResult<Void>
        implements Serializable
{
    public ErrorResult(ServiceCode code, String message)
    {
        super(code, message);
    }

    /**
     * 请求失败
     */
    public static ErrorResult fail(ServiceException e) {return fail(e.getCode(), e.getMessage());}
    /**
     * 请求失败
     */
    public static ErrorResult fail(ServiceCode code, String message) {return new ErrorResult(code, message);}
}