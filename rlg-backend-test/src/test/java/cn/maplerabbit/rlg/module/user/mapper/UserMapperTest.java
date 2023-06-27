package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.pojo.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class UserMapperTest implements BaseCrudTest
{
    @Autowired
    UserMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    public void testSave()
    {
        User user = new User()
                .setUuid("3")
                .setUsername("测试3")
                .setPassword("123456");
        System.out.println(user);
        log.debug("插入{}条数据：\n{}", mapper.save(user), user);
    }

    @Test
    public void testSaveBatch()
    {
        User u1 = new User()
                .setUuid("4")
                .setUsername("测试4")
                .setPassword("123456");
        System.out.println(u1);
        User u2 = new User()
                .setUuid("5")
                .setUsername("测试5")
                .setPassword("123456");
        System.out.println(u2);

        log.debug(
                "插入{}条数据：\n{}",
                mapper.saveBatch(
                        Arrays.asList(u1, u2)
                ),
                u1 + "\n" + u2
        );
    }

    @Test
    public void testRemove()
    {
        System.out.println(
                mapper.remove(2L)
        );
    }

    @Test
    public void testRemoveBatch()
    {
        List<String> list = Arrays.asList(
                "3", "4"
        );

        log.debug("想要删除{}条数据，删除了{}条数据", list.size(), mapper.removeBatch(list));
    }

    @Test
    public void testUpdate()
    {
        User user = new User()
                .setUuid("2")
                .setUuid("10")
                .setUsername("更新测试");
        System.out.println(user);
        log.debug("更新{}条数据", mapper.update(user));
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
        List<User> users = mapper.queryBatch(list);

        log.debug("想要查询{}条数据，查询到{}条数据：\n", list.size(), users.size());
        users.forEach(System.out::println);
    }

    @Test
    public void testCount()
    {
        log.debug("数据数量：{}", mapper.count());
    }
}