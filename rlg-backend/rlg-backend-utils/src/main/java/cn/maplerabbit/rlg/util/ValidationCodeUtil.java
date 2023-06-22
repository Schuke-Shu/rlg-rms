package cn.maplerabbit.rlg.util;

/**
 * 验证码工具
 */
public class ValidationCodeUtil
{
    public static String generate()
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