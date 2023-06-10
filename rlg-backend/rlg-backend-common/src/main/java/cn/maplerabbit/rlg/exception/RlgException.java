package cn.maplerabbit.rlg.exception;

public class RlgException extends Exception
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