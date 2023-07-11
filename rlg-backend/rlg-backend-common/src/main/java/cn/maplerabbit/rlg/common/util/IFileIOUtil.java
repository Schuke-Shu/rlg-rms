package cn.maplerabbit.rlg.common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件io工具
 */
public interface IFileIOUtil
{
    /**
     * 判断是否存在某个文件
     * @param completePath 文件绝对路径
     * @return 是否存在
     */
    boolean exist(String completePath) throws IOException;

    /**
     * 上传文件
     * @param dir 上传文件的目标父目录
     * @param filename 文件名
     * @param in 文件输入流
     * @return 是否上传成功
     */
    boolean upload(String dir, String filename, MultipartFile file) throws IOException;

    /**
     * 下载文件
     * @param dir 目标文件的父目录
     * @param filename 目标文件名
     * @param out 文件输出流
     */
    void download(String dir, String filename, HttpServletResponse response) throws IOException;
}
