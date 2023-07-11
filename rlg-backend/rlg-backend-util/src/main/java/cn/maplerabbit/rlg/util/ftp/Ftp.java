package cn.maplerabbit.rlg.util.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ftp 工具类实现
 */
public class Ftp implements AutoCloseable
{
    private FTPClient client;
    private final FtpClientPool ftpClientPool;

    public Ftp(FtpClientPool ftpClientPool, FTPClient client)
    {
        this.ftpClientPool = ftpClientPool;
        this.client = client;
    }

    public Ftp(FtpClientPool ftpClientPool)
            throws Exception
    {
        this.ftpClientPool = ftpClientPool;
        this.client = ftpClientPool.borrowObject();
    }

    /**
     * 打开指定目录
     *
     * @param directory 目录
     * @return 是否成功打开目录
     */
    public boolean cd(String directory)
            throws IOException
    {
        if (StrUtil.isBlank(directory))
            return false;

        return client.changeWorkingDirectory(directory);
    }

    /**
     * 打开上级目录
     *
     * @return 是否成功打开目录
     */
    public boolean cdup()
            throws IOException
    {
        return client.changeToParentDirectory();
    }

    /**
     * 获取远程当前目录（工作目录）
     *
     * @return 远程当前目录
     */
    public String pwd()
            throws IOException
    {
        return client.printWorkingDirectory();
    }

    /**
     * 以文件名字符串形式获取某个目录下所有文件和目录，不会递归遍历
     *
     * @param path 需要遍历的目录
     * @return 文件和目录列表
     */
    public List<String> ls(String path)
            throws IOException
    {
        FTPFile[] files = lsf(path);
        if (files == null)
            return null;

        return Arrays
                .stream(files)
                .map(FTPFile::getName)
                .collect(Collectors.toList());
    }

    /**
     * 以文件对象形式获取某个目录下所有文件和目录，不会递归遍历
     *
     * @param path 目录
     * @return 文件或目录列表
     */
    public FTPFile[] lsf(String path)
            throws IOException
    {
        String pwd = pwd();

        if (!cd(path))
            return null;

        FTPFile[] ftpFiles;
        try
        {
            ftpFiles = client.listFiles();
        }
        finally
        {
            // 回到原目录
            cd(pwd);
        }

        return ftpFiles;
    }

    /**
     * 在当前远程目录（工作目录）下创建新的目录
     *
     * @param dir 目录名
     */
    public void mkdir(String dir)
            throws IOException
    {
        client.makeDirectory(dir);
    }

    /**
     * 延指定路径创建目录，若中间有目录不存在也会一同创建，创建完成后回到执行操作前的工作目录
     *
     * <p>如果以'/'开头表示从ftp服务器的根目录开始创建，否则从当前工作目录开始创建</p>
     *
     * @param dir 文件夹路径，绝对路径
     */
    public void mkdirs(String dir)
            throws IOException
    {
        String[] dirs = StrUtil.trim(dir).split("[\\\\/]+");
        String now = pwd();

        if (dirs.length > 0 && StrUtil.isEmpty(dirs[0]))
            // 首位为空，表示以/开头
            cd(StrUtil.SLASH);

        for (String d : dirs)
            if (StrUtil.isNotEmpty(d) && !cd(d))
            {
                // 目录不存在时创建
                mkdir(d);
                cd(d);
            }

        // 切换回工作目录
        cd(now);
    }

    /**
     * 文件或目录是否存在
     *
     * <p>若以'/'开头从ftp服务器根目录按照指定路径寻找文件，否则从当前工作目录寻找</p>
     *
     * @param path 目录
     * @return 是否存在
     */
    public boolean exist(String path)
            throws IOException
    {
        String filename = FileUtil.getName(path);

        List<String> fileList = ls(
                StrUtil.removeSuffix(
                        path,
                        filename
                )
        );

        if (fileList == null)
            return false;

        return fileList.contains(filename);
    }

    /**
     * 判断ftp服务器指定目录下是否存在文件
     *
     * @param path 目录路径
     * @return 是否存在
     */
    public boolean existFile(String path)
            throws IOException
    {
        FTPFile[] ftpFileArr;
        ftpFileArr = client.listFiles(path);
        return ArrayUtil.isNotEmpty(ftpFileArr);
    }

    /**
     * 删除指定目录下的指定文件
     *
     * @param path 目录路径
     * @return 是否删除成功
     */
    public boolean deleteFile(String path)
            throws IOException
    {
        String pwd = pwd();
        String fileName = FileUtil.getName(path);
        String dir = StrUtil.removeSuffix(path, fileName);

        cd(dir);
        try
        {
            return client.deleteFile(fileName);
        }
        finally
        {
            // 回到原目录
            cd(pwd);
        }
    }

    /**
     * 删除文件夹及其文件夹下的所有文件
     *
     * @param dirPath 文件夹路径
     * @return boolean 是否删除成功
     */
    public boolean deleteDir(String dirPath)
            throws IOException
    {
        FTPFile[] dirs;
        dirs = client.listFiles(dirPath);

        for (FTPFile ftpFile : dirs)
        {
            String name = ftpFile.getName();
            String childPath = StrUtil.format("{}/{}", dirPath, name);
            if (ftpFile.isDirectory())
            {
                // 上级和本级目录除外
                if (!name.equals(".") && !name.equals(".."))
                    deleteDir(childPath);
            }
            else
                deleteFile(childPath);
        }

        // 删除空目录
        return client.removeDirectory(dirPath);
    }

    /**
     * 上传文件到指定目录，可选：
     *
     * <pre>
     * 1. path为null或""上传到当前路径
     * 2. path为相对路径则相对于当前路径的子路径
     * 3. path为绝对路径则上传到此路径
     * </pre>
     *
     * @param path 服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param file 文件
     * @return 是否上传成功
     */
    public boolean upload(String path, File file)
            throws IOException
    {
        Assert.notNull(file, "File to upload cannot be null");
        return upload(path, file.getName(), file);
    }

    /**
     * 上传文件到指定目录，可选：
     *
     * <pre>
     * 1. path为null或""上传到当前路径
     * 2. path为相对路径则相对于当前路径的子路径
     * 3. path为绝对路径则上传到此路径
     * </pre>
     *
     * @param file     文件
     * @param path     服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param fileName 自定义在服务端保存的文件名
     * @return 是否上传成功
     */
    public boolean upload(String path, String fileName, File file)
            throws IOException
    {
        try (InputStream in = FileUtil.getInputStream(file))
        {
            return upload(path, fileName, in);
        }
    }

    /**
     * 上传文件到指定目录，可选：
     *
     * <pre>
     * 1. path为null或""上传到当前路径
     * 2. path为相对路径则相对于当前路径的子路径
     * 3. path为绝对路径则上传到此路径
     * </pre>
     *
     * @param path       服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param fileName   文件名
     * @param fileStream 文件流
     * @return 是否上传成功
     */
    public boolean upload(String path, String fileName, InputStream fileStream)
            throws IOException
    {
        client.setFileType(FTPClient.BINARY_FILE_TYPE);

        String pwd = pwd();

        if (StrUtil.isNotBlank(path))
        {
            mkdirs(path);
            if (!cd(path))
                return false;
        }

        try
        {
            return client.storeFile(fileName, fileStream);
        }
        finally
        {
            cd(pwd);
        }
    }

    /**
     * 下载文件
     *
     * @param path    文件路径
     * @param outFile 输出文件或目录
     */
    public void download(String path, File outFile)
            throws IOException
    {
        String fileName = FileUtil.getName(path);

        download(
                StrUtil.removeSuffix(path, fileName),
                fileName,
                outFile
        );
    }

    /**
     * 下载文件
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @param outFile  输出文件或目录
     */
    public void download(String path, String fileName, File outFile)
            throws IOException
    {
        if (outFile.isDirectory())
            outFile = new File(outFile, fileName);

        if (!outFile.exists())
            FileUtil.touch(outFile);

        try (OutputStream out = FileUtil.getOutputStream(outFile))
        {
            download(path, fileName, out);
        }
    }

    /**
     * 下载文件到输出流
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @param out      输出位置
     */
    public void download(String path, String fileName, OutputStream out)
            throws IOException
    {
        String pwd =pwd();

        cd(path);
        try
        {
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.retrieveFile(fileName, out);
        }
        finally
        {
            cd(pwd);
        }
    }

    @Override
    public void close()
    {
        ftpClientPool.returnObject(client);
    }

    public void setClient(FTPClient client)
    {
        this.client = client;
    }
}