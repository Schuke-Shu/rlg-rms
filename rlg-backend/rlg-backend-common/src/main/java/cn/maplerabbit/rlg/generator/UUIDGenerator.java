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
    /**
     * UUID容器
     */
    private final List<String> uuidContainer = new LinkedList<>();

    /**
     * 最后一次生成的UUID
     */
    private String latest_uuid = "";

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
        if (uuidContainer.size() == 0) reloadContainer();
        return uuidContainer.remove(0);
    }

    /**
     * 重新填充容器
     */
    private void reloadContainer()
    {
        while (uuidContainer.size() < max)
            uuidContainer.add(re());

        log.debug("UUIDGenerator reloadContainer()...");
        if (max > 1000) log.warn("UUID容器容量：{}，容量过大", max);
    }

    /**
     * 刷新uuid
     */
    private String re()
    {
        String s;
        do
            s = UUID
                    .randomUUID()
                    .toString()
                    .replaceAll("-", "");
        while (latest_uuid.equals(s));
        latest_uuid = s;
        return s;
    }
}
