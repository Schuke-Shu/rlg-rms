package cn.maplerabbit.rlg.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FilePropertiesTest
{
    @Autowired
    private FileProperties file;

    @Test
    void test()
    {
        System.out.println(file);
    }
}