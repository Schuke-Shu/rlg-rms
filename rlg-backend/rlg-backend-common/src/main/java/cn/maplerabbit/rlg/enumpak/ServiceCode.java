package cn.maplerabbit.rlg.enumpak;

/**
 * 状态码
 */
public enum ServiceCode
{
    OK(20000, "请求成功"),
    ERR_BAD_REQUEST(40000, "错误：请求参数格式有误"),
    ERR_UNAUTHORIZED(40100, "错误：登录失败，用户名或密码错误"),
    ERR_UNAUTHORIZED_DISABLED(40110, "错误：登录失败，账号已经被禁用"),
    ERR_FORBIDDEN(40300, "错误：权限不足"),
    ERR_NOT_FOUND(40400, "错误：数据不存在"),
    ERR_CONFLICT(40900, "错误：数据冲突"),
    ERR_INSERT(50000, "错误：插入数据错误"),
    ERR_DELETE(50010, "错误：删除数据错误"),
    ERR_UPDATE(50020, "错误：修改数据错误"),
    ERR_SELECT(50030, "错误：查询数据错误"),
    ERR_REQUEST_FAIL(50100, "错误：请求失败"),
    ERR_JWT_EXPIRED(60100, "错误：JWT过期"),
    ERR_JWT_SIGNATURE(60200, "错误：JWT签名错误"),
    ERR_JWT_MALFORMED(60300, "错误：JWT格式错误"),
    ERR_UNKNOWN(99999, "错误：未知错误");

    private final Integer status;
    private final String msg;

    private ServiceCode(Integer status, String msg)
    {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus()
    {
        return status;
    }
    public String getMsg()
    {
        return msg;
    }
}
