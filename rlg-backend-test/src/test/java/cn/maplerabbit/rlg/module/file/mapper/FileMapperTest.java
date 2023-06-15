package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.pojo.file.entity.File;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class FileMapperTest
{
    @Autowired
    FileMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    void testSave() // ok
    {
        System.out.println(
                mapper.save(
                        new File()
                                .setUuid("1")
                                .setUserUuid("1")
                                .setSize(0L)
                )
        );
    }

    @Test
    void testSaveBatch() // ok
    {
        System.out.println(
                mapper.saveBatch(
                        Arrays.asList(
                                new File()
                                        .setUuid("2")
                                        .setUserUuid("2")
                                        .setSize(0L),
                                new File()
                                        .setUuid("3")
                                        .setUserUuid("3")
                                        .setSize(0L)
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
                        new File()
                                .setUuid("1")
                                .setUserUuid("123")
                )
        );
    }

    @Test
    void testQuery() // ok
    {
        System.out.println(
                mapper.query("1")
        );
    }

    @Test
    void testQueryBatch() // ok
    {
        mapper.queryBatch(
                        Arrays.asList(
                                "2", "3"
                        )
                )
                .forEach(System.out::println);
    }
}