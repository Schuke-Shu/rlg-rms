package cn.maplerabbit.rlg.common.enumpak;

import cn.maplerabbit.rlg.common.exception.ServiceException;
import cn.maplerabbit.rlg.common.property.RlgProperties;
import cn.maplerabbit.rlg.common.property.SpringProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.StringUtils;

/**
 * 登录账号类型枚举
 */
public enum AccountType
{
    /**
     * 用户名
     */
    USERNAME,
    /**
     * 邮箱
     */
    EMAIL,
    /**
     * 手机号
     */
    PHONE;

    /**
     * 获取数据库字段
     */
    public String field()
    {
        return this.name().toLowerCase();
    }
}