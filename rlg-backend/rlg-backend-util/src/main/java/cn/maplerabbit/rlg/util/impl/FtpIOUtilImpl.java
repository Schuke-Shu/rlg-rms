package cn.maplerabbit.rlg.util.impl;

import icu.mabbit.mdk4j.core.util.ObjectUtil;
import cn.maplerabbit.rlg.common.exception.FtpError;
import cn.maplerabbit.rlg.common.util.IFileIOUtil;
import cn.maplerabbit.rlg.util.ftp.Ftp;
import cn.maplerabbit.rlg.util.ftp.FtpClientPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.tomcat.util.collections.SynchronizedStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ftp实现文件上传与下载
 */
@Slf4j
@Component("ftpIOUtil")
public class FtpIOUtilImpl
        implements IFileIOUtil
{
    @Autowired
    private FtpClientPool ftpClientPool;

    /**
     * 缓存Ftp对象消除Ftp频繁创建和销毁丢失的性能
     */
    private SynchronizedStack<Ftp> ftpCache = new SynchronizedStack<>();

    public FtpIOUtilImpl()
    {
        log.debug("FtpIOUtilImpl()...");
    }

    /**
     * 获取ftp
     */
    private Ftp ftp()
    {
        try
        {
            Ftp ftp = ftpCache.pop();
            FTPClient client = ftpClientPool.borrowObject();

            if (ObjectUtil.isEmpty(ftp))
                ftp = new Ftp(ftpClientPool, client);
            else
                ftp.setClient(client);

            return ftp;
        }
        catch (Exception e)
        {
            throw new FtpError("Failed to get ftp client, msg: {}" + e.getMessage());
        }
    }

    @Override
    public boolean exist(String completePath)
            throws IOException
    {
        try (Ftp ftp = ftp())
        {
            boolean exist = ftp.exist(completePath);
            ftpCache.push(ftp);
            return exist;
        }
    }

    @Override
    public boolean upload(String dir, String filename, MultipartFile file)
            throws IOException
    {
        try (Ftp ftp = ftp(); InputStream is = file.getInputStream())
        {
            ftp.upload(dir, filename, is);
            ftpCache.push(ftp);
            return true;
        }
    }

    @Override
    public void download(String dir, String filename, HttpServletResponse response)
            throws IOException
    {
        response.setContentType("multipart/form-data");

        try (Ftp ftp = ftp(); OutputStream os = response.getOutputStream())
        {
            ftp.download(dir, filename, os);
            ftpCache.push(ftp);
        }
    }
}