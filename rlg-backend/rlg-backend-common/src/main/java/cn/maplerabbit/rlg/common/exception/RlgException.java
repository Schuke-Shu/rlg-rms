package cn.maplerabbit.rlg.common.exception;

/**
 * 自定义运行时异常
 */
public class RlgException
        extends RuntimeException
{
    public RlgException()
    {
        super();
    }

    public RlgException(String message)
    {
        super(message);
    }

    public RlgException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public RlgException(Throwable cause)
    {
        super(cause);
    }

    protected RlgException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}