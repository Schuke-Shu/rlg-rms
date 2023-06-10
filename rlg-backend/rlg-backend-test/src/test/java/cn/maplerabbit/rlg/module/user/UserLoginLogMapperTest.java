package cn.maplerabbit.rlg.module.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class UserLoginLogMapperTest
{
    @Autowired
    UserLoginLogMapper mapper;

    @Test
    void testSave() // ok
    {
        System.out.println(
                mapper.save(
                        new UserLoginLog()
                                .setUserUuid("123")
                                .setUsername("测试")
                )
        );
    }

    @Test
    void testSaveBatch() // ok
    {
        System.out.println(
                mapper.saveBatch(
                        Arrays.asList(
                                new UserLoginLog()
                                        .setUserUuid("1")
                                        .setUsername("测试"),
                                new UserLoginLog()
                                        .setUserUuid("2")
                                        .setUsername("root")
                        )
                )
        );
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