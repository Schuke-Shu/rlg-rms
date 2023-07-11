package cn.maplerabbit.rlg.common.exception;

import cn.maplerabbit.rlg.common.entity.ErrorDetail;

/**
 * 程序错误
 */
public class ProgramError
        extends RuntimeException
{
    public ProgramError(String msg) {super(msg);}
}