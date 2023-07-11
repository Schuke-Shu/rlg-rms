package cn.maplerabbit.rlg.common.util;

/**
 * 验证码工具
 * <p>验证码存储到redis时，使用<strong>“请求地址:账户”</strong>的形式作为key值，所以要求<strong>获取与验证时的接口地址要相同</strong></p>
 * <p>获取时采用get请求，验证时采用post请求</p>
 */
public interface ICodeUtil
{
    /**
     * 验证
     * @param provide   获取到的验证码
     * @param own       存储的验证码
     * @return 是否验证成功
     */
    void validate(String provide, String own);

    /**
     * 生成6位随机验证码（包含大小写字母与数字）
     */
    String generate();
}