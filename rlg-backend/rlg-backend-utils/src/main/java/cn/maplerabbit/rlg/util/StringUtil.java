package cn.maplerabbit.rlg.util;

import java.util.StringJoiner;

/**
 * 字符串工具
 */
public class StringUtil
{
    /**
     * 快速创建StringBuilder
     */
    public static StringBuilder build(Object... args)
    {
        StringBuilder sb = new StringBuilder();
        for (Object o: args)
            sb.append(o);
        return sb;
    }

    /**
     * 快速创建StringJoiner
     */
    public static StringJoiner join(String delimiter, String prefix, String suffix, CharSequence... args)
    {
        StringJoiner sj = new StringJoiner(delimiter, prefix, suffix);
        for (CharSequence c: args)
            sj.add(c);
        return sj;
    }
    /**
     * 快速创建StringJoiner
     */
    public static StringJoiner join(String delimiter,CharSequence... args)
    {
        return join(delimiter, "", "", args);
    }
}