package cn.maplerabbit.rlg.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil<V>
{
    @Autowired
    private RedisTemplate<String, V> redisTemplate;

    public RedisUtil() {log.debug("RedisUtil()...");}

    public V get(String key)
    {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置redis键值对
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean set(String key, V value)
    {
        log.debug("Create redis data, key: {}, value: {}", key, value);
        try
        {
            redisTemplate.opsForValue().set(key, value);
            return true;
        }
        catch (Exception e)
        {
            log.error("RedisUtil set key: {} value: {} failed", key, value);
            log.error("-- Exception: {}, msg: {}", e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

    /**
     * 设置值的同时设置过期时间（单位：分钟）
     * @param key   键
     * @param value 值
     * @param time  有效时间
     * @return 是否设置成功
     */
    public boolean set(String key, V value, int time)
    {
        log.debug("Create redis data with timeout, key: {}, value: {}, time: {}m", key, value, time);
        try
        {
            if (time > 0)
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
            else
                set(key, value);
            return true;
        }
        catch (Exception e)
        {
            log.error("RedisUtil set key: {} value: {} failed", key, value);
            log.error("-- Exception: {}, msg: {}", e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

    /**
     * 使用字符串数组组成key，以冒号分隔
     * @param args 字符串数组
     * @return key
     */
    public String key(String... args)
    {
        StringJoiner joiner = new StringJoiner(":");
        for (String arg : args) joiner.add(arg);
        return joiner.toString();
    }

    /**
     * 删除redis数据
     */
    public void remove(String key)
    {
        log.debug("Remove redis date by key: {}", key);
        if (! Boolean.TRUE.equals(redisTemplate.delete(key)))
            log.warn("Redis remove failed, key: {}", key);
    }
}