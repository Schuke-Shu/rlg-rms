package cn.maplerabbit.rlg.util.ftp;

import cn.maplerabbit.rlg.common.exception.FtpError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

import static cn.maplerabbit.rlg.common.property.RlgProperties.FileProperties.FtpProperties;

@Slf4j
public class FtpClientFactory
        extends BasePooledObjectFactory<FTPClient>
{
    private final FtpProperties ftpProperties;

    public FtpClientFactory(FtpProperties ftpProperties)
    {
        this.ftpProperties = ftpProperties;
    }

    @Override
    public FTPClient create()
    {
        final FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding(ftpProperties.getEncoding());
        ftpClient.setConnectTimeout(ftpProperties.getConnectTimeout());

        try
        {
            // 连接ftp服务器
            ftpClient.connect(ftpProperties.getHost(), ftpProperties.getPort());
            // 登录ftp服务器
            ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
        }
        catch (IOException e)
        {
            throw new FtpError("Failed to connect ftp");
        }

        // 判断是否成功登录服务器
        int replyCode = ftpClient.getReplyCode();

        log.trace("Reply code: {}", replyCode);

        if (!FTPReply.isPositiveCompletion(replyCode))
        {
            try
            {
                ftpClient.disconnect();
            }
            catch (IOException e)
            {
                log.error("-- IOException, msg: {}", e.getMessage());
            }
            throw new FtpError(
                    String.format(
                            "Ftp login failed, user: %s, reply code: %s",
                            ftpProperties.getUsername(),
                            replyCode
                    )
            );
        }

        ftpClient.setBufferSize(ftpProperties.getBufferSize());

        // 设置被动模式
        if (ftpProperties.getPassiveMode())
            ftpClient.enterLocalPassiveMode();

        return ftpClient;
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient obj)
    {
        return new DefaultPooledObject<>(obj);
    }

    /**
     * 销毁FtpClient对象
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> ftpPooled)
    {
        if (ftpPooled == null)
        {
            return;
        }

        FTPClient ftpClient = ftpPooled.getObject();
        try
        {
            if (ftpClient.isConnected())
                ftpClient.logout();
        }
        catch (IOException io)
        {
            log.error("Ftp client logout failed, msg: {}", io.getMessage());
        }
        finally
        {
            try
            {
                ftpClient.disconnect();
            }
            catch (IOException io)
            {
                log.error("Close ftp client failed, msg: {}", io.getMessage());
            }
        }
    }

    /**
     * 验证FtpClient对象是否还可用
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> ftpPooled)
    {
        try
        {
            return ftpPooled.getObject().sendNoOp();
        }
        catch (IOException e)
        {
            log.error("Failed to validate client, msg: {}", e.getMessage());
        }
        return false;
    }
}