package cn.maplerabbit.rlg.module.file.exception;

import cn.maplerabbit.rlg.common.exception.ProgramError;

/**
 * 文件模块程序异常
 */
public class FileError extends ProgramError
{
    public FileError(String msg)
    {
        super(msg);
    }
}