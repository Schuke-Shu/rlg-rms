package cn.maplerabbit.rlg.common.entity.result;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * Json响应数据承载类
 */
@Getter
public class JsonResult<T>
        implements Serializable
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
        this.status = code.getStatus();
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

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("status=").append(status)
                .append(", message='").append(message).append('\'')
                .append(", data=").append(data)
                .append('}')
                .toString();
    }
}