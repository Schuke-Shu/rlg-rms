package cn.maplerabbit.rlg.handler;

import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.RlgException;
import cn.maplerabbit.rlg.web.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
    public GlobalExceptionHandler()
    {
        log.debug("GlobalExceptionHandler()...");
    }

    @ExceptionHandler
    public ErrorResult handleRlgException(RlgException e)
    {
        log.debug("-- RlgException，code：{}，msg：{}", e.getCode().getValue(), e.getMessage());
        return ErrorResult.fail(e);
    }

    @ExceptionHandler
    public ErrorResult handleBindException(BindException e) {
        log.debug("-- BindException，msg：{}", e.getMessage());

        return ErrorResult.fail(
                ServiceCode.ERR_BAD_REQUEST,
                e.getFieldError().getDefaultMessage()
        );
    }
}