package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.pojo.user.entity.UserPermission;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class UserPermissionMapperTest implements BaseCrudTest
{
    @Autowired
    UserPermissionMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    public void testSave()
    {
        UserPermission permission = new UserPermission()
                .setFlag("测试1")
                .setDescription("这是一条测试数据");
        System.out.println(permission);
        log.debug("插入{}条数据：\n{}", mapper.save(permission), permission);
    }

    @Test
    public void testSaveBatch()
    {
        UserPermission p2 = new UserPermission()
                .setFlag("测试2")
                .setDescription("这是一条测试数据");
        System.out.println(p2);
        UserPermission p3 = new UserPermission()
                .setFlag("测试3")
                .setDescription("这是一条测试数据");
        System.out.println(p3);

        log.debug(
                "插入{}条数据：\n{}",
                mapper.saveBatch(
                        Arrays.asList(p2, p3)
                ),
                p2 + "\n" + p3
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
        UserPermission permission = new UserPermission()
                .setId(1L)
                .setFlag("更新测试1")
                .setDescription("这是一条测试数据");
        System.out.println(permission);
        log.debug("更新{}条数据", mapper.update(permission));
    }

    @Test
    public void testQuery()
    {
        System.out.println(
                mapper.query(1L)
        );
    }

    @Test
    public void testQueryBatch()
    {
        List<Long> list = Arrays.asList(
                2L, 3L
        );
        List<UserPermission> permissions = mapper.queryBatch(list);

        log.debug("想要查询{}条数据，查询到{}条数据：\n", list.size(), permissions.size());
        permissions.forEach(System.out::println);
    }

    @Test
    public void testCount()
    {
        log.debug("数据数量：{}", mapper.count());
    }
}