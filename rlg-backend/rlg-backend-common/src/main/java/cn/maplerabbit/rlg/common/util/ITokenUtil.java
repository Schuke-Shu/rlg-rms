package cn.maplerabbit.rlg.common.util;

import java.util.Date;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * token工具
 */
public interface ITokenUtil
{
    /**
     * 生成token
     * <p>自定义有效时间、签名算法、token类型</p>
     *
     * @param claims 要存储的数据
     * @param timeout 过期时间
     * @param algorithm 签名算法
     * @param type token类型
     * @return token字符串
     */
    String generate(Map<String, Object> claims, Date timeout, String algorithm, String type, String secretKey);

    /**
     * 生成token
     * <p>自定义有效时间</p>
     * <p>签名算法、token类型在配置文件中定义</p>
     *
     * @param claims 要存储的数据
     * @param timeout 过期时间
     * @return token字符串
     */
    String generate(Map<String, Object> claims, Date timeout);

    /**
     * 生成token
     * <p>有效时间、签名算法、token类型全部在配置文件中定义</p>
     *
     * @param claims 要存储的数据
     * @return token字符串
     */
    public String generate(Map<String, Object> claims);

    /**
     * 根据有效时限获取具体的过期时间
     * @param usableMinutes token有效时间（单位：分钟）
     * @return 具体过期时间
     */
    default Date timeout(int usableMinutes)
    {
        return new Date(
                System.currentTimeMillis() + MINUTES.toMillis(usableMinutes)
        );
    }
}