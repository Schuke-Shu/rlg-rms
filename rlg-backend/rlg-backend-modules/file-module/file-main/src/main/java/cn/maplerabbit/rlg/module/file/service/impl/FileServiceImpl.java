package cn.maplerabbit.rlg.module.file.service.impl;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.FtpError;
import cn.maplerabbit.rlg.common.util.IErrorUtil;
import cn.maplerabbit.rlg.common.util.IFileIOUtil;
import cn.maplerabbit.rlg.module.file.exception.FileCreateException;
import cn.maplerabbit.rlg.module.file.mapper.FileMapper;
import cn.maplerabbit.rlg.module.file.service.IFileService;
import cn.maplerabbit.rlg.pojo.file.entity.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class FileServiceImpl
        implements IFileService
{
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private IErrorUtil errorUtil;
    @Resource // 文件IO工具有两个实现类，这里使用Resource注解，通过名称注入
    private IFileIOUtil ftpIOUtil;

    private static final String FTP_ROOT = "/";

    public FileServiceImpl()
    {
        log.debug("FileServiceImpl()...");
    }

    @Override
    public File ftpExist(String sha512, Long size)
    {
        return fileMapper.queryBySha512AndSize(sha512, size);
    }

    @Override
    @Transactional
    public void upload(File file, MultipartFile multipartFile)
    {

        // 存储文件
        if (fileMapper.save(file) < 1)
            // 存储失败
            throw new FileCreateException(ServiceCode.ERR_INSERT, "文件上传失败，请稍后再试");

        // 上传至ftp服务器
        try
        {
            ftpIOUtil.upload(FTP_ROOT, file.getUuid(), multipartFile);
        }
        catch (IOException e)
        {
            errorUtil.record(this.getClass(), e, FtpError.class);
        }
    }
}