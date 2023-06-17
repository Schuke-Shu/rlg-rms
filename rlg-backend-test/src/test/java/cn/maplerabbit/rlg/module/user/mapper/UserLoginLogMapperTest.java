package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.pojo.log.entity.UserLoginLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class UserLoginLogMapperTest
{
    @Autowired
    UserLoginLogMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    void testSave() // ok
    {
        UserLoginLog test1 = new UserLoginLog()
                .setUserUuid("1")
                .setUsername("测试");

        System.out.println(
                mapper.save(test1)
        );

        System.out.println(test1.getId());
    }

    @Test
    void testSaveBatch() // ok
    {
        UserLoginLog test2 = new UserLoginLog()
                .setUserUuid("1")
                .setUsername("测试");
        UserLoginLog test3 = new UserLoginLog()
                .setUserUuid("2")
                .setUsername("root");

        System.out.println(
                mapper.saveBatch(
                        Arrays.asList(
                                test2, test3
                        )
                )
        );

        System.out.println(test2.getId());
        System.out.println(test3.getId());
    }

    @Test
    void testRemove() //ok
    {
        System.out.println(
                mapper.remove(1L)
        );
    }

    @Test
    void testRemoveBatch() // ok
    {
        System.out.println(
                mapper.removeBatch(
                        Arrays.asList(
                                2L, 3L
                        )
                )
        );
    }

    @Test
    void update() // ok
    {
        System.out.println(
                mapper.update(
                        new UserLoginLog()
                                .setId(1L)
                                .setUserUuid("123456")
                )
        );
    }

    @Test
    void testQuery() // ok
    {
        System.out.println(
                mapper.query(1L)
        );
    }

    @Test
    void testQueryBatch() // ok
    {
        mapper.queryBatch(
                Arrays.asList(
                        1L, 2L, 3L
                )
        )
                .forEach(System.out::println);
    }
}