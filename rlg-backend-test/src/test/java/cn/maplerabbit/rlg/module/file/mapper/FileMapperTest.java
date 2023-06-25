package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.pojo.file.entity.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class FileMapperTest implements BaseCrudTest
{
    @Autowired
    FileMapper mapper;

    /* ========== Base CRUD ========== */
    @Test
    public void testSave()
    {
        File file = new File()
                .setUuid("1")
                .setUserId(1L)
                .setSize(12L);
        System.out.println(file);
        log.debug("插入{}条数据：\n{}", mapper.save(file), file);
    }

    @Test
    public void testSaveBatch()
    {
        File f1 = new File()
                .setUuid("4")
                .setUserId(2L)
                .setSize(45L);
        System.out.println(f1);
        File f2 = new File()
                .setUuid("5")
                .setUserId(2L)
                .setSize(78L);
        System.out.println(f2);

        log.debug(
                "插入{}条数据：\n{}",
                mapper.saveBatch(
                        Arrays.asList(f1, f2)
                ),
                f1 + "\n" + f2
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
        File file = new File()
                .setUuid("4")
                .setUserId(1L)
                .setSize(100L);
        System.out.println(file);
        log.debug("更新{}条数据", mapper.update(file));
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
        List<File> files = mapper.queryBatch(list);

        log.debug("想要查询{}条数据，查询到{}条数据：\n", list.size(), files.size());
        files.forEach(System.out::println);
    }

    @Test
    public void testCount() {
        System.out.println(mapper.count());
    }
}