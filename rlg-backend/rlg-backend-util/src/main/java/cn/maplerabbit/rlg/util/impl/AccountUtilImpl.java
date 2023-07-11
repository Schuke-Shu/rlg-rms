package cn.maplerabbit.rlg.util.impl;

import cn.maplerabbit.rlg.common.enumpak.AccountType;
import cn.maplerabbit.rlg.common.property.RlgProperties;
import cn.maplerabbit.rlg.common.property.SpringProperties;
import cn.maplerabbit.rlg.common.util.IAccountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static cn.maplerabbit.rlg.common.enumpak.AccountType.*;

@Slf4j
@Component
public class AccountUtilImpl implements IAccountUtil
{
    @Autowired
    private RlgProperties rlgProperties;
    @Autowired
    private SpringProperties springProperties;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 邮箱正则表达式
     */
    String EMAIL_REGEX = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    /**
     * 手机号正则表达式
     */
    String PHONE_REGEX = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    public AccountType parse(String account)
    {
        if (account == null) return null;

        if (account.matches(EMAIL_REGEX)) return EMAIL;
        if (account.matches(PHONE_REGEX)) return PHONE;
        return USERNAME;
    }

    /**
     * 获取验证码发送器，若账户格式不符合邮箱或手机号，返回<code>null</code>
     */
    public CodeSender getSender(String account)
    {
        AccountType type = parse(account);

        switch (type)
        {
            case EMAIL: return MAIL_SENDER;
            case PHONE: return MSG_SENDER;
            default: return null;
        }
    }

    private final CodeSender MAIL_SENDER = new CodeSender() {
        @Override
        public void send(String account, String code)
        {
            // 设置邮件内容
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(
                    springProperties
                            .getMail()
                            .getUsername()
            );
            msg.setTo(account);
            msg.setSubject(
                    rlgProperties
                            .getCode()
                            .getTitle()
            );
            msg.setText(
                    String.format(
                            rlgProperties.getCode().getTextFormat(),
                            code
                    )
            );

            // 发送邮件
            mailSender.send(msg);
        }
    };

    private final CodeSender MSG_SENDER = new CodeSender() {
        @Override
        public void send(String account, String code)
        {

        }
    };
}
