package cn.maplerabbit.rlg.web;

import cn.maplerabbit.rlg.enumpak.ServiceCode;

/**
 * 请求成功
 * @param <T> 要返回的数据类型
 */
public class SuccessResult<T> extends JsonResult<T>
{
    public SuccessResult(ServiceCode code, T data)
    {
        super(code, data);
    }

    /**
     * 请求成功（无响应数据）
     */
    public static JsonResult<Void> ok() {return ok(null);}
    /**
     * 请求成功
     */
    public static <T> JsonResult<T> ok(T data) {return new JsonResult<>(ServiceCode.SUCCESS, data);}
}