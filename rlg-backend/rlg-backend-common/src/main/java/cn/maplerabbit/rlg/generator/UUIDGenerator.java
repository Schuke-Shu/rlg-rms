package cn.maplerabbit.rlg.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * UUID生成器，内置一个链表集合充当容器
 */
@Component
@Slf4j
public class UUIDGenerator
{
    /**
     * UUID容器
     */
    private List<String> uuidContainer = new LinkedList<>();

    /**
     * 上一次生成的UUID
     */
    private String uuid = UUID
            .randomUUID()
            .toString()
            .replaceAll("-", "");

    public UUIDGenerator()
    {
        log.trace("UUIDGenerator()...");
    }

    /**
     * 获取uuid
     */
    public String get()
    {
        if (uuidContainer.size() == 0) reloadContainer();
        return uuidContainer.remove(0);
    }

    /**
     * 最多存储的UUID数量
     */
    @Value("${rlg.uuid.max:10}")
    private int max;


    /**
     * 重新填充容器
     */
    private void reloadContainer()
    {
        while (uuidContainer.size() < max)
        {
            uuidContainer.add(uuid);
            re();
        }

        log.trace("reloadContainer()...\nuuidContainer‘s capacity: {}", max);
    }

    /**
     * 刷新uuid
     */
    private void re()
    {
        String s;
        do
            s = UUID
                    .randomUUID()
                    .toString()
                    .replaceAll("-", "");
        while (uuid.equals(s));
        uuid = s;
    }
}
