package cn.maplerabbit.rlg.util.impl;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.CodeException;
import cn.maplerabbit.rlg.common.util.ICodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 验证码工具
 * <p>验证码存储到redis时，使用<strong>“请求地址:账户”</strong>的形式作为key值，所以要求<strong>获取与验证时的接口地址要相同</strong></p>
 * <p>获取时采用get请求，验证时采用post请求</p>
 */
@Slf4j
@Component
public class CodeUtilImpl implements ICodeUtil
{
    public CodeUtilImpl() {log.debug("ValidationCodeUtil()...");}

    /**
     * 验证，验证成功不做处理，验证失败抛出{@link CodeException}
     * @param provide   获取到的验证码
     * @param own       存储的验证码
     */
    public void validate(String provide, String own)
    {
        if (own == null)
            throw new CodeException(ServiceCode.ERR_NOT_FOUND, "验证码已过期");
        if (! provide.equalsIgnoreCase(own))
            throw new CodeException(ServiceCode.ERR_BAD_REQUEST, "验证码错误");
    }

    /**
     * 生成6位随机验证码（包含大小写字母与数字）
     */
    public String generate()
    {
        String temp = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++)
            sb.append(
                    temp.charAt(
                            (int) (Math.random() * temp.length())
                    )
            );

        String code = sb.toString();
        log.debug("Validation code: {}", code);
        return code;
    }
}