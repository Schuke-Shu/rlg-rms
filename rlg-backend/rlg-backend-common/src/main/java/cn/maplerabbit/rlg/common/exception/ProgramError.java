package cn.maplerabbit.rlg.common.exception;

/**
 * 程序错误
 */
public class ProgramError
        extends RuntimeException
{
    private String message;

    public ProgramError(String message)
    {
        this.message = message;
    }
}