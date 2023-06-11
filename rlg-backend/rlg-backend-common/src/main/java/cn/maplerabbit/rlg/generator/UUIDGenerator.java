package cn.maplerabbit.rlg.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * UUID生成器
 */
@Component
@Slf4j
public class UUIDGenerator
{
    private Object lock = new Object();
    /**
     * UUID容器
     */
    private final List<String> uuidContainer = new LinkedList<>();

    /**
     * 最后一次生成的UUID
     */
    private String uuid = "";

    /**
     * UUID容器的最大容量
     */
    @Value("${rlg.uuid.max:10}")
    private int max;

    public UUIDGenerator()
    {
        log.trace("UUIDGenerator()...");
    }

    /**
     * 获取uuid
     */
    public String get()
    {
        if (uuidContainer.isEmpty())
            synchronized (lock)
            {
                if (uuidContainer.isEmpty())
                    reloadContainer();
            }
        return uuidContainer.remove(0);
    }

    /**
     * 重新填充容器
     */
    private void reloadContainer()
    {
        log.debug("reload UUID container...");

        while (uuidContainer.size() < max) {
            // refresh uuid
            String s = uuid;
            do
                uuid = UUID
                        .randomUUID()
                        .toString()
                        .replaceAll("-", "");
            while (uuid.equals(s));

            uuidContainer.add(uuid);
        }
    }
}
