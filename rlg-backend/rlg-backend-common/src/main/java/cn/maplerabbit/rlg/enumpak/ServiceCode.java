package cn.maplerabbit.rlg.enumpak;

/**
 * 状态码
 */
public enum ServiceCode
{
    /**
     * 成功
     */
    SUCCESS(20000);

    private final Integer value;

    ServiceCode(Integer value) {this.value = value;}

    public Integer getValue() {return value;}
}
