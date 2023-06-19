package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.constpak.FileUnitConst;
import cn.maplerabbit.rlg.pojo.file.entity.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class FileMapperTest implements BaseCrudTest, FileUnitConst
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
                .setSize(12)
                .setSizeUnit(UNIT_KB);
        System.out.println(file);
        log.debug("插入{}条数据：\n{}", mapper.save(file), file);
    }

    @Test
    public void testSaveBatch()
    {
        File f1 = new File()
                .setUuid("4")
                .setUserId(2L)
                .setSize(45)
                .setSizeUnit(UNIT_B);
        System.out.println(f1);
        File f2 = new File()
                .setUuid("5")
                .setUserId(2L)
                .setSize(78)
                .setSizeUnit(UNIT_GB);
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
                mapper.remove("1")
        );
    }

    @Test
    public void testRemoveBatch()
    {
        List<String> list = Arrays.asList(
                "2", "3"
        );

        log.debug("想要删除{}条数据，删除了{}条数据", list.size(), mapper.removeBatch(list));
    }

    @Test
    public void testUpdate()
    {
        File file = new File()
                .setUuid("4")
                .setUserId(1L)
                .setSize(100)
                .setSizeUnit(UNIT_TB);
        System.out.println(file);
        log.debug("更新{}条数据", mapper.update(file));
    }

    @Test
    public void testQuery()
    {
        System.out.println(
                mapper.query("4")
        );
    }

    @Test
    public void testQueryBatch()
    {
        List<String> list = Arrays.asList(
                "4", "5"
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