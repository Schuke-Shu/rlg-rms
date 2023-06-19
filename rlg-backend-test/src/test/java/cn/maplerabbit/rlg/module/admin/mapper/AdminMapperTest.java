package cn.maplerabbit.rlg.module.admin.mapper;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.pojo.admin.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class AdminMapperTest implements BaseCrudTest
{
    @Autowired
    AdminMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    public void testSave()
    {
        Admin admin = new Admin()
                .setCreatorId(2L)
                .setAdminName("测试1")
                .setPassword("123456");
        System.out.println(admin);
        log.debug("插入{}条数据：\n{}", mapper.save(admin), admin);
    }

    @Test
    public void testSaveBatch()
    {
        Admin a1 = new Admin()
                .setCreatorId(2L)
                .setAdminName("测试2")
                .setPassword("123456");
        System.out.println(a1);
        Admin a2 = new Admin()
                .setCreatorId(2L)
                .setAdminName("测试3")
                .setPassword("123456");
        System.out.println(a2);

        log.debug(
                "插入{}条数据：\n{}",
                mapper.saveBatch(
                        Arrays.asList(a1, a2)
                ),
                a1 + "\n" + a2
        );
    }

    @Test
    public void testRemove()
    {
        System.out.println(
                mapper.remove(3L)
        );
    }

    @Test
    public void testRemoveBatch()
    {
        List<Long> list = Arrays.asList(
                4L, 5L
        );

        log.debug("想要删除{}条数据，删除了{}条数据", list.size(), mapper.removeBatch(list));
    }

    @Test
    public void testUpdate()
    {
        Admin admin = new Admin()
                .setId(3L)
                .setCreatorId(1L)
                .setAdminName("更新测试")
                .setPassword("123456");
        System.out.println(admin);
        log.debug("更新{}条数据", mapper.update(admin));
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
        List<Admin> admins = mapper.queryBatch(list);

        log.debug("想要查询{}条数据，查询到{}条数据：\n", list.size(), admins.size());
        admins.forEach(System.out::println);
    }

    @Test
    public void testCount()
    {
        log.debug("数据数量：{}", mapper.count());
    }
}