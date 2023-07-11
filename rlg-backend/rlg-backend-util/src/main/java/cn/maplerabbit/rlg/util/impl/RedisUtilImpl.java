package cn.maplerabbit.rlg.util.impl;

import cn.maplerabbit.rlg.common.util.IRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtilImpl<V> implements IRedisUtil<V>
{
    @Autowired
    private RedisTemplate<String, V> redisTemplate;

    public RedisUtilImpl() {log.debug("RedisUtilImpl()...");}

    public V get(String key)
    {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public void set(String key, V value)
    {
        log.debug("Create redis data, key: {}, value: {}", key, value);
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, V value, int time)
    {
        log.debug("Create redis data with timeout, key: {}, value: {}, time: {}m", key, value, time);

        if (time > 0)
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
        else
            set(key, value);
    }

    public void remove(String key)
    {
        log.debug("Remove redis date by key: {}", key);
        if (! Boolean.TRUE.equals(redisTemplate.delete(key)))
            log.warn("Redis remove failed, key: {}", key);
    }
}