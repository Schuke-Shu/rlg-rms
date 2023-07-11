package cn.maplerabbit.rlg.common.util;

import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.exception.ProgramError;

/**
 * 错误处理工具
 */
public interface IErrorUtil
{

    /**
     * 向客户端返回异常信息
     */
    void response(ErrorResult result);

    /**
     * 记录异常信息
     * @param from  抛出异常的类
     * @param error 异常
     */
    default void record(Class<?> from, Throwable error)
    {
        record(from, error, null);
    }

    /**
     * 记录异常信息，并抛出{@link ProgramError}，抛出的异常由调用者决定，可以是其本身或其子类
     * @param from  抛出异常的类
     * @param error 异常
     * @param thr   需要抛出的程序异常类
     */
    void record(Class<?> from, Throwable error, Class<? extends ProgramError> thr);
}