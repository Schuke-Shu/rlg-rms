package cn.maplerabbit.rlg.util;

import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.CodeException;
import cn.maplerabbit.rlg.property.CodeProperties;
import cn.maplerabbit.rlg.property.MailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private RedisUtil<String> redisUtil;
    @Autowired
    private CodeProperties codeProperties;

    public void sendEmail(String requestPath, String email)
    {
        // 生成验证码
        String code = generateCode();

        // 将验证码存储到redis中
        saveCode(requestPath, email, code);

        // 设置邮件内容
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailProperties.getUsername());
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
    }

    /**
     * 验证
     * @param requestPath   请求地址
     * @param account       账户名称
     * @param code          验证码
     * @return 是否验证成功
     */
    public void validate(String requestPath, String account, String code)
    {
        String key = redisUtil.key(requestPath, account);
        String value = redisUtil.get(key);

        if (value == null)
            throw new CodeException(ServiceCode.ERR_NOT_FOUND, "验证码已过期");
        if (! value.equals(code))
            throw new CodeException(ServiceCode.ERR_BAD_REQUEST, "验证码错误");

        redisUtil.remove(key);
    }

    /**
     * 向redis存储验证码，请求地址与账户名称将会组成key
     * @param requestPath   请求地址
     * @param account       账户名称
     * @param code          验证码
     */
    private void saveCode(String requestPath, String account, String code)
    {
        String key = redisUtil.key(requestPath, account);

        if ( !
                redisUtil.set(
                        key,
                        code,
                        codeProperties.getUsableTime()
                )
        )
            throw new CodeException(ServiceCode.ERR_INSERT, "验证码发送失败，请稍后再试");

        log.debug("Save code to redis, key: {}, value: {}", key, code);
    }

    /**
     * 生成6位随机验证码（包含大小写字母与数字）
     */
    private String generateCode()
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