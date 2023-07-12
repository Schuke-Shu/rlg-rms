package cn.maplerabbit.rlg.module.file.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.ServiceException;

/**
 * 文件模块异常
 */
public class FileException
        extends ServiceException
{
    public FileException(ServiceCode code, String message)
    {
        super(code, message);
    }
}