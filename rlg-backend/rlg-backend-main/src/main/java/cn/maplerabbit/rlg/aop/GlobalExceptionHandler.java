package cn.maplerabbit.rlg.aop;

import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.ServiceException;
import cn.maplerabbit.rlg.web.ErrorResult;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ErrorResult handleServiceException(ServiceException e)
    {
        log.debug("-- ServiceException，code：{}，msg：{}", e.getCode().getStatus(), e.getMessage());
        return ErrorResult.fail(e);
    }

    @ExceptionHandler
    public ErrorResult handleBindException(BindException e)
    {
        log.debug("-- BindException，msg：{}", e.getMessage());

        return ErrorResult.fail(ServiceCode.ERR_BAD_REQUEST, e.getFieldError().getDefaultMessage());
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
        return ErrorResult.fail(ServiceCode.ERR_UNAUTHORIZED_DISABLED, "账号已经被禁用！");
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
    public ErrorResult handleExpiredJwtException(ExpiredJwtException e)
    {
        log.debug("-- ExpiredJwtException, msg: {}", e.getMessage());
        return ErrorResult.fail(ServiceCode.ERR_JWT_EXPIRED, "您的登录信息已过期，请重新登录");
    }

    @ExceptionHandler
    public ErrorResult handleThrowable(Throwable e) {
        log.error("-- Unhandled Exception: {}", e.getClass().getName());
        e.printStackTrace();
        return ErrorResult.fail(ServiceCode.ERR_UNKNOWN, "服务器忙，请稍后再试");
    }
}