package cn.maplerabbit.rlg.module.file.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;

/**
 * 文件索引创建异常
 */
public class FileCreateException
        extends FileException
{
    public FileCreateException(ServiceCode code, String message)
    {
        super(code, message);
    }
}