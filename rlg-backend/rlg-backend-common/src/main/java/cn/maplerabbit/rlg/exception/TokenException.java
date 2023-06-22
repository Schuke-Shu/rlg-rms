package cn.maplerabbit.rlg.exception;

/**
 * 票据异常
 */
public class TokenException extends RlgException
{
    public TokenException()
    {
        super();
    }

    public TokenException(String message)
    {
        super(message);
    }

    public TokenException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public TokenException(Throwable cause)
    {
        super(cause);
    }

    protected TokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}