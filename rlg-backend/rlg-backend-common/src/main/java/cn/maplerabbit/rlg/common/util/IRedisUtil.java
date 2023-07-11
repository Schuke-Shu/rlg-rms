package cn.maplerabbit.rlg.common.util;

import java.util.StringJoiner;

/**
 * redis工具
 * @param <V> value类型
 */
public interface IRedisUtil<V>
{
    /**
     * 通过key获取value
     * @param key   键
     * @return      值
     */
    V get(String key);

    /**
     * 设置redis键值对
     * @param key   键
     * @param value 值
     */
    void set(String key, V value);

    /**
     * 设置值的同时设置过期时间（单位：分钟）
     * @param key   键
     * @param value 值
     * @param time  有效时间
     */
    void set(String key, V value, int time);

    /**
     * 通过key删除redis数据
     * @param key   键
     */
    void remove(String key);

    /**
     * 使用字符串数组组成key，以冒号分隔
     * @param args 字符串数组
     * @return key
     */
    default String key(String... args)
    {
        StringJoiner joiner = new StringJoiner(":");
        for (String arg : args) joiner.add(arg);
        return joiner.toString();
    }
}