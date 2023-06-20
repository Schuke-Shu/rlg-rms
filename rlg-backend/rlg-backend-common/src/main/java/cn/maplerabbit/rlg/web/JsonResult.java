package cn.maplerabbit.rlg.web;

import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.RlgException;
import lombok.experimental.Accessors;

/**
 * Json响应数据承载类
 */
public class JsonResult<T>
{
    /**
     * 业务状态码
     */
    private final Integer status;
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
        this.status = code.getValue();
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
}