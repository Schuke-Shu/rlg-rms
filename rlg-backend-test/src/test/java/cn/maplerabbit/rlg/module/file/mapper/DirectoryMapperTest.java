package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.pojo.file.entity.Directory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class DirectoryMapperTest implements BaseCrudTest
{
    @Autowired
    DirectoryMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    public void testSave()
    {
        Directory directory = new Directory()
                .setUserId(2L)
                .setFilename("测试1");
        System.out.println(directory);
        log.debug("插入{}条数据：\n{}", mapper.save(directory), directory);
    }

    @Test
    public void testSaveBatch()
    {
        Directory d1 = new Directory()
                .setUserId(1L)
                .setFilename("测试2");
        System.out.println(d1);
        Directory d2 = new Directory()
                .setUserId(2L)
                .setFilename("测试3");
        System.out.println(d2);

        log.debug(
                "插入{}条数据：\n{}",
                mapper.saveBatch(
                        Arrays.asList(d1, d2)
                ),
                d1 + "\n" + d2
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
        Directory directory = new Directory()
                .setId(4L)
                .setUserId(2L)
                .setFilename("更新测试");
        System.out.println(directory);
        log.debug("更新{}条数据", mapper.update(directory));
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
        List<Directory> directories = mapper.queryBatch(list);

        log.debug("想要查询{}条数据，查询到{}条数据：\n", list.size(), directories.size());
        directories.forEach(System.out::println);
    }

    @Test
    public void testCount() {
        System.out.println(mapper.count());
    }
}