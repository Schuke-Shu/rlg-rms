package cn.maplerabbit.rlg.module.file;

import cn.maplerabbit.rlg.BaseCrudTest;
import cn.maplerabbit.rlg.module.file.mapper.FileMapper;
import cn.maplerabbit.rlg.pojo.file.entity.File;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileMapperTest
        implements BaseCrudTest<File, String, FileMapper>
{
    @Autowired
    private FileMapper mapper;

    @Override
    public FileMapper mapper()
    {
        return mapper;
    }

    @Test
    @Override
    public void baseCrudTest()
            throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        File f1 = new File(uuid(), null, null, "typeTest", 123L, LocalDateTime.now(), null, null);
        File f2 = new File(uuid(), null, null, "typeTest", 123L, LocalDateTime.now(), null, null);
        File f3 = new File(uuid(), null, null, "typeTest", 123L, LocalDateTime.now(), null, null);

        File update = new File(null, null, null, "updateTest", 456L, LocalDateTime.now(), null, null);

        baseCrudTest("uuid", update, f1, f2, f3);
    }

    public static String uuid()
    {
        return
                UUID
                        .randomUUID()
                        .toString()
                        .replaceAll("-", "");
    }
}