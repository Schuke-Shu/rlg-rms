package cn.maplerabbit.rlg.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtPropertiesTest
{
    @Autowired
    private JwtProperties jwt;

    @Test
    void test()
    {
        System.out.println(jwt);
    }
}