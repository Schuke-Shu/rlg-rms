package cn.maplerabbit.rlg.util;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.FtpError;
import cn.maplerabbit.rlg.common.util.IErrorUtil;
import cn.maplerabbit.rlg.module.user.exception.UserException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ErrorUtilTest
{
    @Autowired
    private IErrorUtil errorUtil;

    @Test
    void testRecord()
    {
        errorUtil.record(this.getClass(), new UserException(ServiceCode.ERR_UNKNOWN ,"test"));
    }

    @Test
    void testRecordAndThrow() // ok
    {
        errorUtil.record(this.getClass(), new Exception("test"), FtpError.class);
    }
}