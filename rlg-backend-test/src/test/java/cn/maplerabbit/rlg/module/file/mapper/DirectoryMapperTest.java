package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.pojo.file.entity.Directory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class DirectoryMapperTest
{
    @Autowired
    DirectoryMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    void testSave() // ok
    {

        Directory test1 = new Directory()
                .setFileUuid("1")
                .setUserId(1L)
                .setFilename("测试1");

        System.out.println(
                mapper.save(test1)
        );
        System.out.println(test1.getId());
    }

    @Test
    void testSaveBatch() // ok
    {
        Directory test2 = new Directory()
                .setFileUuid("2")
                .setUserId(2L)
                .setFilename("测试2");
        Directory test3 = new Directory()
                .setFileUuid("3")
                .setUserId(3L)
                .setFilename("测试3");

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
                        new Directory()
                                .setId(1L)
                                .setFilename("更新测试1")
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