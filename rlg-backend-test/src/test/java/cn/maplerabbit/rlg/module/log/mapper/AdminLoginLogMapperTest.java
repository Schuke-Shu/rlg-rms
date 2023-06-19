package cn.maplerabbit.rlg.module.log.mapper;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.pojo.log.entity.AdminLoginLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class AdminLoginLogMapperTest implements BaseCrudTest
{
    @Autowired
    AdminLoginLogMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    public void testSave()
    {
        AdminLoginLog loginLog = new AdminLoginLog()
                .setAdminId(1L)
                .setEngine("这是一条测试数据");
        System.out.println(loginLog);
        log.debug("插入{}条数据：\n{}", mapper.save(loginLog), loginLog);
    }

    @Test
    public void testSaveBatch()
    {
        AdminLoginLog a1 = new AdminLoginLog()
                .setAdminId(1L)
                .setEngine("这是一条测试数据");
        System.out.println(a1);
        AdminLoginLog a2 = new AdminLoginLog()
                .setAdminId(1L)
                .setEngine("这是一条测试数据");
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
        AdminLoginLog loginLog = new AdminLoginLog()
                .setId(4L)
                .setAdminId(1L)
                .setEngine("更新测试");
        System.out.println(loginLog);
        log.debug("更新{}条数据", mapper.update(loginLog));
    }

    @Test
    public void testQuery()
    {
        System.out.println(
                mapper.query(4L)
        );
    }

    @Test
    public void testQueryBatch()
    {
        List<Long> list = Arrays.asList(
                4L, 5L
        );
        List<AdminLoginLog> logs = mapper.queryBatch(list);

        log.debug("想要查询{}条数据，查询到{}条数据：\n", list.size(), logs.size());
        logs.forEach(System.out::println);
    }

    @Test
    public void testCount() {
        System.out.println(mapper.count());
    }
}