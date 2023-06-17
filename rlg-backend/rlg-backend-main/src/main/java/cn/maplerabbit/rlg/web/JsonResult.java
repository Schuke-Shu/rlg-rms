package cn.maplerabbit.rlg.web;

import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.RlgException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Json响应数据承载类
 */
@Accessors(chain = true)
public class JsonResult<T>
{
    /**
     * 业务状态码
     */
    private final Integer code;
    /**
     * 失败时的提示信息
     */
    private final String message;
    /**
     * 成功时的返回数据
     */
    private final T data;

    /**
     * 全参构造
     */
    public JsonResult(ServiceCode code, String message, T data)
    {
        this.code = code.value();
        this.message = message;
        this.data = data;
    }
    /**
     * 请求成功的构造方法
     */
    public JsonResult(ServiceCode code, String message) {this(code, message, null);}
    /**
     * 请求失败的构造方法
     */
    public JsonResult(ServiceCode code, T data) {this(code, null, data);}

    /**
     * 请求成功（无响应数据）
     */
    public static <T> JsonResult<T> ok() {return ok(null);}
    /**
     * 请求成功
     */
    public static <T> JsonResult<T> ok(T data) {return new JsonResult<>(ServiceCode.SUCCESS, data);}
    /**
     * 请求失败
     */
    public static <T> JsonResult<T> fail(RlgException e) {return fail(e.code(), e.getMessage());}
    /**
     * 请求失败
     */
    public static <T> JsonResult<T> fail(ServiceCode code, String message) {return new JsonResult<>(code, message);}

    public Integer getCode()
    {
        return code;
    }
    public String getMessage()
    {
        return message;
    }
    public T getData()
    {
        return data;
    }
}