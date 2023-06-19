package cn.maplerabbit.rlg.module.admin.mapper;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.pojo.admin.entity.AdminRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class AdminRoleMapperTest implements BaseCrudTest
{
    @Autowired
    AdminRoleMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    public void testSave()
    {
        AdminRole role = new AdminRole()
                .setFlag("测试1")
                .setDescription("这是一条测试数据");
        System.out.println(role);
        log.debug("插入{}条数据：\n{}", mapper.save(role), role);
    }

    @Test
    public void testSaveBatch()
    {
        AdminRole r2 = new AdminRole()
                .setFlag("测试2")
                .setDescription("这是一条测试数据");
        System.out.println(r2);
        AdminRole r3 = new AdminRole()
                .setFlag("测试3")
                .setDescription("这是一条测试数据");
        System.out.println(r3);

        log.debug(
                "插入{}条数据：\n{}",
                mapper.saveBatch(
                        Arrays.asList(r2, r3)
                ),
                r2 + "\n" + r3
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
        AdminRole role = new AdminRole()
                .setId(1L)
                .setFlag("更新测试1")
                .setDescription("这是一条测试数据");
        System.out.println(role);
        log.debug("更新{}条数据", mapper.update(role));
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
        List<AdminRole> roles = mapper.queryBatch(list);

        log.debug("想要查询{}条数据，查询到{}条数据：\n", list.size(), roles.size());
        roles.forEach(System.out::println);
    }

    @Test
    public void testCount()
    {
        log.debug("数据数量：{}", mapper.count());
    }
}