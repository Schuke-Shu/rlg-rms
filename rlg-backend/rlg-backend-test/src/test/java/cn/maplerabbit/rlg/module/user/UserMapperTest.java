package cn.maplerabbit.rlg.module.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class UserMapperTest
{
    @Autowired
    UserMapper mapper;

    @Test
    void testSave() // ok
    {
        System.out.println(
                mapper.save(
                        new User()
                                .setUuid("3")
                                .setUsername("测试2")
                                .setPassword("123456")
                )
        );
    }

    @Test
    void testSaveBatch() // ok
    {
        System.out.println(
                mapper.saveBatch(
                        Arrays.asList(
                                new User()
                                        .setUuid("4")
                                        .setUsername("测试3")
                                        .setPassword("123456"),
                                new User()
                                        .setUuid("5")
                                        .setUsername("测试4")
                                        .setPassword("123456")
                        )
                )
        );
    }

    @Test
    void testRemove() //ok
    {
        System.out.println(
                mapper.remove(3L)
        );
    }

    @Test
    void testRemoveBatch() // ok
    {
        System.out.println(
                mapper.removeBatch(
                        Arrays.asList(
                                4L, 5L
                        )
                )
        );
    }

    @Test
    void update() // ok
    {
        System.out.println(
                mapper.update(
                        new User()
                                .setUuid("3")
                                .setUsername("更新测试")
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
                                2L, 3L
                        )
                )
                .forEach(System.out::println);
    }
}