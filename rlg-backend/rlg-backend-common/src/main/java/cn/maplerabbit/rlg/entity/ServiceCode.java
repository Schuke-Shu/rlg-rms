package cn.maplerabbit.rlg.entity;

/**
 * <p>状态码</p>
 * <p>状态码应由模块层代码-控制层代码-服务层代码组成，每一部分是两位十六进制数</p>
 * <p>创建时由父到子创建，从父到子以此为模块层-控制层-服务层，创建时在构造方法传入父代码类即可</p>
 * <p>模块层代码由ModuleCodeProperties属性类维护，控制层和服务层代码由各控制器维护</p>
 * <p><strong>请求成功的代码是特殊的状态码，它的值为0</strong></p>
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
