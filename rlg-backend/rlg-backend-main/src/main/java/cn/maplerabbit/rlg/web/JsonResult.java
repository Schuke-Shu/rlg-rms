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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JsonResult
{
    /**
     * 业务状态码
     */
    private Integer code;
    /**
     * 失败时的提示信息
     */
    private String message;
    /**
     * 成功时的返回数据
     */
    private Object data;

    public JsonResult(Integer code, String message) {this(code, message, null);}
    public JsonResult(Integer code, Object data) {this(code, null, data);}

    public static JsonResult ok() {return ok(null);}
    public static JsonResult ok(Object data)
    {
        return new JsonResult(ServiceCode.SUCCESS.value(), data);
    }
    public static JsonResult fail(RlgException e) {
        return fail(e.code(), e.getMessage());
    }
    public static JsonResult fail(ServiceCode code, String message) {
        return new JsonResult(code.value(), message);
    }
}