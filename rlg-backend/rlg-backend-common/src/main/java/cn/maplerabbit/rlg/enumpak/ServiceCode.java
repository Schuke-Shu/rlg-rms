package cn.maplerabbit.rlg.enumpak;

/**
 * 状态码
 */
public enum ServiceCode
{
    /**
     * 成功
     */
    SUCCESS(20000),
    /**
     * 错误：请求参数格式有误
     */
    ERR_BAD_REQUEST(40000),
    /**
     * 错误，多次重复发送请求
     */
    ERR_REPEAT_REQUEST(40001);

    private final Integer value;

    ServiceCode(Integer value) {this.value = value;}

    public Integer getValue() {return value;}
}
