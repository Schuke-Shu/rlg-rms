package cn.maplerabbit.rlg.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ApacheDigestUtilsTest
{
    @Test
    public void test()
            throws IOException
    {
        File file = new File("C:\\test.zip");
        FileInputStream fileInputStream = new FileInputStream(file);
        String hex = DigestUtils.sha512Hex(fileInputStream);
        System.out.println(hex);
    }
}