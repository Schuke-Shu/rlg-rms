package cn.maplerabbit.rlg.util.config;

import cn.maplerabbit.rlg.common.property.RlgProperties;
import cn.maplerabbit.rlg.util.ftp.FtpClientPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * FtpClient存活检测
 */
@Slf4j
@Component
public class FtpClientAliveCheck
        implements InitializingBean
{
    private final AliveCheckThread ALIVE_CHECK_THREAD = new AliveCheckThread();

    @Autowired
    private FtpClientPool ftpClientPool;
    @Autowired
    private RlgProperties rlgProperties;

    public static final String THREAD_NAME = "ftp-client-alive-check";

    @Override
    public void afterPropertiesSet()
            throws Exception
    {
        // 启动心跳检测线程
        new Thread(ALIVE_CHECK_THREAD, THREAD_NAME)
                .start();
    }

    private class AliveCheckThread
            implements Runnable
    {
        @Override
        public void run()
        {
            while (true)
            {
                BlockingQueue<FTPClient> pool = ftpClientPool.getFtpBlockingQueue();

                if (!ObjectUtils.isEmpty(pool))
                    for (FTPClient client : pool)
                        check(client);

                // 每30s发送一次心跳
                sleep(
                        SECONDS.toMillis(
                                rlgProperties
                                        .getFile()
                                        .getFtp()
                                        .getPool()
                                        .getAliveCheckIntervalTime()
                        )
                );
            }

        }

        private void check(FTPClient client)
        {
            try
            {
                if (!client.sendNoOp())
                {
                    ftpClientPool.invalidateObject(client);
                    log.warn("Ftp: [{}] has dead", client);
                }
                log.trace("Ftp: [{}] is alive", client);
            }
            catch (Exception e)
            {
                log.error("Exception of ftp client alive check, msg: {}", e.getMessage());
                ftpClientPool.invalidateObject(client);
            }
        }

        private void sleep(long millis)
        {
            try
            {
                Thread.sleep(millis);
            }
            catch (InterruptedException e)
            {
                log.error("Ftp sleep exception, msg: {}", e.getMessage());
            }
        }
    }
}