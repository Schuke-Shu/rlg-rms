package cn.maplerabbit.rlg.aop;

import cn.maplerabbit.rlg.common.enumpak.DirectoryEnum;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.ProgramError;
import cn.maplerabbit.rlg.common.exception.ServiceException;
import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.property.RlgProperties;
import cn.maplerabbit.rlg.common.util.IErrorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.StringJoiner;

import static cn.maplerabbit.rlg.common.enumpak.DirectoryEnum.ERROR_LOG;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @Autowired
    private IErrorUtil errorUtil;
    @Autowired
    private RlgProperties rlgProperties;

    public GlobalExceptionHandler()
    {
        log.debug("GlobalExceptionHandler()...");
    }

    @ExceptionHandler
    public ErrorResult handleServiceException(ServiceException e)
    {
        log.debug("-- {}，code：{}，msg：{}", e.getClass().getSimpleName(), e.getCode().getStatus(), e.getMessage());
        return ErrorResult.fail(e);
    }

    @ExceptionHandler
    public ErrorResult handleProgramError(ProgramError e)
    {
        e.printStackTrace(); // 生产环境关闭
        log.error("-- {}，msg：{}", e.getClass().getSimpleName(), e.getMessage());
        return ErrorResult.fail(ServiceCode.ERR_UNKNOWN, "服务器忙，请稍后再试");
    }

    @ExceptionHandler
    public ErrorResult handleBindException(BindException e)
    {
        log.debug("-- BindException，msg：{}", e.getMessage());
        return ErrorResult.fail(ServiceCode.ERR_BAD_REQUEST, e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler
    public ErrorResult handleConstraintViolationException(ConstraintViolationException e)
    {
        String msg;
        StringJoiner joiner = new StringJoiner(", ");
        e
                .getConstraintViolations()
                .forEach(ex -> {
                    log.debug("-- ConstraintViolationException, msg:{}", ex.getMessage());
                    joiner.add(ex.getMessage());
                });

        msg = joiner.toString();
        return ErrorResult.fail(ServiceCode.ERR_BAD_REQUEST, msg);
    }

    @ExceptionHandler({
            InternalAuthenticationServiceException.class,
            BadCredentialsException.class
    })
    public ErrorResult handleAuthenticationException(AuthenticationException e)
    {
        log.debug("-- AuthenticationException, ExceptionName: {}, msg: {}", e.getClass().getName(), e.getMessage());
        return ErrorResult.fail(ServiceCode.ERR_UNAUTHORIZED, "用户名或密码错误");
    }

    @ExceptionHandler
    public ErrorResult handleDisabledException(DisabledException e)
    {
        log.debug("-- DisabledException, msg: {}", e.getMessage());
        return ErrorResult.fail(ServiceCode.ERR_UNAUTHORIZED, "账号已经被禁用！");
    }

    @ExceptionHandler
    public ErrorResult handleAccessDeniedException(AccessDeniedException e)
    {
        log.debug("-- AccessDeniedException, msg: {}", e.getMessage());
        return ErrorResult.fail(ServiceCode.ERR_FORBIDDEN, "权限不足，禁止访问");
    }

    @ExceptionHandler
    public ErrorResult handleDuplicateKeyException(DuplicateKeyException e)
    {
        log.debug("-- DuplicateKeyException, msg: {}", e.getMessage());
        return ErrorResult.fail(ServiceCode.ERR_INSERT, "请求发送失败，请重试");
    }

    @ExceptionHandler
    public ErrorResult handleMailException(MailException e)
    {
        log.debug("-- MailException, msg: {}", e.getMessage());
        return ErrorResult.fail(ServiceCode.ERR_REQUEST_FAIL, "邮件发送失败，请检查邮箱账号，或者稍后再试");
    }

    @ExceptionHandler
    public ErrorResult handleThrowable(Throwable e) {
        e.printStackTrace(); // 生产环境关闭
        log.error(
                "-- Unhandled Exception: {}, please check error-log file: {}",
                e.getClass().getName(),
                ERROR_LOG.router(
                        rlgProperties
                                .getFile()
                                .getRootDir()
                )
        );
        errorUtil.record(this.getClass(), e);
        return ErrorResult.fail(ServiceCode.ERR_UNKNOWN, "服务器忙，请稍后再试");
    }
}