package cn.maplerabbit.rlg.module.file.exception;

import cn.maplerabbit.rlg.common.enumpak.ServiceCode;

/**
 * 文件索引创建异常
 */
public class DictionaryCreateException
        extends FileException
{
    public DictionaryCreateException(ServiceCode code, String message)
    {
        super(code, message);
    }
}