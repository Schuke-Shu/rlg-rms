package cn.maplerabbit.rlg.common.enumpak;

import org.springframework.util.Assert;

/**
 * 登录账号类型枚举
 */
public enum AccountType
{
    /**
     * 用户名
     */
    USERNAME("username"),
    /**
     * 邮箱
     */
    EMAIL("email"),
    /**
     * 手机号
     */
    MOBILE("phone");

    /**
     * 邮箱正则表达式
     */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    /**
     * 手机号正则表达式
     */
    private static final String MOBILE_REGEX = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    /**
     * 数据库中对应登陆方式的字段
     */
    private final String field;

    AccountType(String field) {this.field = field;}

    public static AccountType parse(String account)
    {
        Assert.hasText(account, "Account cannot be empty！");
        if (account.matches(EMAIL_REGEX)) return EMAIL;
        if (account.matches(MOBILE_REGEX)) return MOBILE;
        return USERNAME;
    }

    public String getField()
    {
        return field;
    }
}