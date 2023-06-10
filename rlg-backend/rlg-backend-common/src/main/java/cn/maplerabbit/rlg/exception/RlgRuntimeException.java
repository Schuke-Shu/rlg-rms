package cn.maplerabbit.rlg.exception;

public class RlgRuntimeException extends RuntimeException
{
    public RlgRuntimeException()
    {
        super();
    }

    public RlgRuntimeException(String message)
    {
        super(message);
    }

    public RlgRuntimeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public RlgRuntimeException(Throwable cause)
    {
        super(cause);
    }

    protected RlgRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}