package cn.maplerabbit.rlg.util;

import java.io.File;

public class FileUtil
{
    /**
     * @param path 文件路径
     */
    public static File file(String path)
    {
        return new File(path);
    }
    /**
     * @param parent 父目录
     * @param filename 文件名
     */
    public static File file(String parent, String filename)
    {
        return new File(parent, filename);
    }
    /**
     * @param parent 父目录文件对象
     * @param filename 文件名
     */
    public static File file(File parent, String filename)
    {
        return new File(parent, filename);
    }

    /**
     * @param path 目录路径
     */
    public static boolean mkdirs(String path)
    {
        return file(path).mkdirs();
    }

    /**
     * @param parent 父目录路径
     * @param dirname 目录名称
     */
    public static boolean mkdirs(String parent, String dirname)
    {
        return file(parent, dirname).mkdirs();
    }

    /**
     * @param parent 父目录文件对象
     * @param dirname 目录名称
     */
    public static boolean mkdirs(File parent, String dirname)
    {
        return file(parent, dirname).mkdirs();
    }
}