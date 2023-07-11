package cn.maplerabbit.rlg.common.util;

import cn.maplerabbit.rlg.common.enumpak.AccountType;

public interface IAccountUtil
{
    /**
     * 解析账户类型
     */
    AccountType parse(String account);

    /**
     * 获取验证码发送器
     */
    CodeSender getSender(String account);

    /**
     * 验证码发送器
     */
    abstract class CodeSender
    {
        /**
         * 发送验证码
         * @param code 验证码
         */
        public abstract void send(String account, String code);
    }
}
