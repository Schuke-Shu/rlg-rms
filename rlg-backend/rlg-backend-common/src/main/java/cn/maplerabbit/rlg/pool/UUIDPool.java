package cn.maplerabbit.rlg.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.UUID;

/**
 * UUID池
 */
@Component
@Slf4j
public class UUIDPool
{
    private final LinkedList<String> pool = new LinkedList<>();

    /**
     * UUID池的最大容量
     */
    @Value("${rlg.uuid.max:10}")
    private int max;

    private String uuid = uuid();

    public UUIDPool()
    {
        log.debug("UUIDPool()...");
    }

    /**
     * 获取uuid
     */
    public String get()
    {
        if (pool.isEmpty()) reloadContainer();

        return pool.poll();
    }

    /**
     * 重新填充容器，加锁保证线程安全
     */
    private synchronized void reloadContainer()
    {
        if (!pool.isEmpty())
            return;

        log.debug("reload UUID container...");
        // 将uuid添加到pool中，然后刷新uuid
        while (pool.size() < max) {pool.add(uuid);refresh();}
    }

    /**
     * 刷新uuid
     */
    private void refresh()
    {
        for (String s = uuid; s.equals(uuid); uuid = uuid());
    }

    /**
     * 生成uuid
     */
    private String uuid()
    {
        return UUID
                .randomUUID()
                .toString()
                .replaceAll("-", "");
    }
}
