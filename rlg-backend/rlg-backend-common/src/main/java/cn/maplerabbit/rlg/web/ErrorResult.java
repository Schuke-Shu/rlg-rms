package cn.maplerabbit.rlg.web;

import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.RlgException;

public class ErrorResult extends JsonResult<Void>
{
    public ErrorResult(ServiceCode code, String message)
    {
        super(code, message);
    }

    /**
     * 请求失败
     */
    public static ErrorResult fail(RlgException e) {return fail(e.getCode(), e.getMessage());}
    /**
     * 请求失败
     */
    public static ErrorResult fail(ServiceCode code, String message) {return new ErrorResult(code, message);}
}