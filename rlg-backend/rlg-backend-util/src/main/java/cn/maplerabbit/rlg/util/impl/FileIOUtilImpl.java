package cn.maplerabbit.rlg.util.impl;

import cn.maplerabbit.rlg.common.util.IFileIOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

/**
 * JavaIO流实现文件上传与下载
 */
@Slf4j
@Component("fileIOUtil")
public class FileIOUtilImpl
        implements IFileIOUtil
{
    public FileIOUtilImpl()
    {
        log.debug("FileIOUtilImpl()...");
    }

    @Override
    public boolean exist(String completePath) throws IOException {return new File(completePath).exists();}

    @Override
    public boolean upload(String dir, String filename, MultipartFile file)
            throws IOException
    {
        FileCopyUtils.copy(
                file.getInputStream(),
                Files.newOutputStream(
                        new File(dir, filename)
                                .toPath()
                )
        );
        return true;
    }

    @Override
    public void download(String dir, String filename, HttpServletResponse response)
            throws IOException
    {
        response.setContentType("multipart/form-data");

        FileCopyUtils.copy(
                Files.newInputStream(
                        new File(dir, filename)
                                .toPath()
                ),
                response.getOutputStream()
        );
    }
}