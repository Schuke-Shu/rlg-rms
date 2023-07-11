package cn.maplerabbit.rlg.util.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.BaseObjectPool;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static cn.maplerabbit.rlg.common.property.RlgProperties.FileProperties.FtpProperties.FtpPoolProperties;

@Slf4j
public class FtpClientPool
        extends BaseObjectPool<FTPClient>
        implements InitializingBean
{
    private BlockingQueue<FTPClient> ftpBlockingQueue;
    private final FtpClientFactory ftpClientFactory;

    private final FtpPoolProperties ftpPoolProperties;

    public FtpClientPool(FtpPoolProperties ftpPoolProperties, FtpClientFactory factory)
            throws Exception
    {
        this.ftpClientFactory = factory;
        this.ftpPoolProperties = ftpPoolProperties;
    }

    @Override
    public void afterPropertiesSet()
            throws Exception
    {
        // 初始化连接池
        ftpBlockingQueue = new ArrayBlockingQueue<>(ftpPoolProperties.getSize());
        addObjects(ftpPoolProperties.getSize());
    }

    /**
     * 获取连接
     */
    @Override
    public FTPClient borrowObject()
            throws Exception
    {
        FTPClient client = ftpBlockingQueue.take();

        // 如果连接对象为空，创建对象并放入连接池
        if (ObjectUtils.isEmpty(client))
            // 创建Ftp连接放入连接池
            returnObject(
                    ftpClientFactory.create()
            );
        // 如果连接对象无效，销毁该对象并创建新的连接对象放入连接池
        else if (
                !ftpClientFactory.validateObject(
                        ftpClientFactory.wrap(client)
                )
        )
        {
            // 对无效的对象进行处理
            invalidateObject(client);
            // 创建新的对象并放入连接池
            returnObject(
                    ftpClientFactory.create()
            );
        }

        return client;
    }

    @Override
    public void returnObject(FTPClient client)
    {
        try
        {
            if (client != null && !offer(client))
                ftpClientFactory.destroyObject(
                        ftpClientFactory.wrap(client)
                );
        }
        catch (InterruptedException e)
        {
            log.error("Return ftp client interrupted, msg: {}", e.getMessage());
        }
    }

    @Override
    public void invalidateObject(FTPClient client)
    {
        try
        {
            // 若连接对象仍处于连接状态，退出连接
            if (client.isConnected())
                client.logout();
        }
        catch (IOException io)
        {
            log.error("Ftp client logout failed, msg: {}", io.getMessage());
        }
        finally
        {
            try
            {
                client.disconnect();
            }
            catch (IOException io)
            {
                log.error("Close ftp client failed, msg: {}", io.getMessage());
            }

            // 从队列中删除连接对象
            ftpBlockingQueue.remove(client);
        }
    }

    /**
     * 增加一个新的链接
     */
    @Override
    public void addObject()
            throws Exception
    {
        // 插入对象到队列
        offer(ftpClientFactory.create());
    }

    /**
     * 关闭连接池
     */
    @Override
    public void close()
    {
        try
        {
            Iterator<FTPClient> iterator = ftpBlockingQueue.iterator();
            while (iterator.hasNext())
                ftpClientFactory.destroyObject(
                        ftpClientFactory.wrap(
                                ftpBlockingQueue.take()
                        )
                );
        }
        catch (Exception e)
        {
            log.error("-- {}, msg: {}", e.getClass().getSimpleName(), e.getMessage());
            log.error("Close ftp client ftpBlockingQueue failed");
        }
    }

    private boolean offer(FTPClient ftpClient)
            throws InterruptedException
    {
        return ftpBlockingQueue.offer(
                ftpClient, ftpPoolProperties.getOfferTimeout(), TimeUnit.SECONDS
        );
    }

    public BlockingQueue<FTPClient> getFtpBlockingQueue()
    {
        return ftpBlockingQueue;
    }
}