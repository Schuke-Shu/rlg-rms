package cn.maplerabbit.rlg.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 验证码工具
 */
@Slf4j
@Component
public class ValidationCodeUtil
{
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送邮箱的账户
     */
    @Value("${spring.mail.username}")
    private String mailSenderAccount;

    public String sendEmail(String email)
    {
        // 生成验证码
        String code = generate();

        // 设置邮件内容
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailSenderAccount);
        msg.setTo(email);
        msg.setSubject("红叶园验证码");
        msg.setText(
                String.format(
                        "您的验证码为：%s",
                        code
                )
        );

        // 发送邮件
        mailSender.send(msg);

        // 返回验证码，供后续使用
        return code;
    }

    /**
     * 生成6位随机验证码（包含大小写字母与数字）
     */
    private String generate()
    {
        String temp = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++)
            sb.append(
                    temp.charAt(
                            (int) (Math.random() * temp.length())
                    )
            );

        return sb.toString();
    }
}