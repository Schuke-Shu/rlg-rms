package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.BaseCudTest;
import cn.maplerabbit.rlg.entity.AssociateUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class RolePermissionMapperTest
        implements BaseCudTest
{
    @Autowired
    RolePermissionMapper mapper;

    /* ========== Base CUD ========== */
    @Test
    public void testSave()
    {
        AssociateUnit<Long, Long> unit = new AssociateUnit<Long, Long>()
                .setLeft(1L)
                .setRight(1L);
        System.out.println(unit);
        log.debug("插入{}条数据：\n{}", mapper.save(unit), unit);
    }

    @Test
    public void testSaveBatch()
    {
        AssociateUnit<Long, Long> u2 = new AssociateUnit<Long, Long>()
                .setLeft(2L)
                .setRight(2L);
        System.out.println(u2);
        AssociateUnit<Long, Long> u3 = new AssociateUnit<Long, Long>()
                .setLeft(3L)
                .setRight(3L);
        System.out.println(u3);

        log.debug(
                "插入{}条数据：\n{}",
                mapper.saveBatch(
                        Arrays.asList(u2, u3)
                ),
                u2 + "\n" + u3
        );
    }

    @Test
    public void testRemove()
    {
        System.out.println(
                mapper.remove(1L)
        );
    }

    @Test
    public void testRemoveBatch()
    {
        List<Long> list = Arrays.asList(
                2L, 3L
        );

        log.debug("想要删除{}条数据，删除了{}条数据", list.size(), mapper.removeBatch(list));
    }

    @Test
    public void testUpdate()
    {
        AssociateUnit<Long, Long> unit = new AssociateUnit<Long, Long>()
                .setId(1L)
                .setLeft(10L)
                .setRight(10L);
        System.out.println(unit);
        log.debug("更新{}条数据", mapper.update(unit));
    }

    @Test
    public void testCount()
    {
        log.debug("数据数量：{}", mapper.count());
    }
}