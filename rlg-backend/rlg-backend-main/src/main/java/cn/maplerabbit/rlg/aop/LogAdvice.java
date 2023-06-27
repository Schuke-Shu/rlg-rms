package cn.maplerabbit.rlg.aop;

import cn.maplerabbit.rlg.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 日志记录类
 */
@Slf4j
@Aspect
@Component
public class LogAdvice
{
    @Autowired
    HttpServletRequest request;

    public LogAdvice()
    {
        log.debug("LogAdvice()...");
    }

    /**
     * 匹配所有模块中的控制器
     */
    @Around("execution(* cn.maplerabbit.rlg.module.*.controller.*.*(..))")
    public Object log(ProceedingJoinPoint pjp)
            throws Throwable
    {
        log.debug("--> Enter controller: {}", pjp.getTarget().getClass().getSimpleName());
        log.debug(
                "Get Request:\ntime: {}\nContent-type: {}\nip: {}\nmethod: {}\ntarget: {}",
                LocalDateTime
                        .now()
                        .toString()
                        .replace('T', ' '),
                request.getHeader("content-type"),
                IpUtil.getIp(request),
                request.getMethod(),
                request.getRequestURI()
        );
        log.debug(
                "Controller method --> {}",
                pjp.getSignature()
        );
        log.trace("args: {}", pjp.getArgs());

        Object result = pjp.proceed();

        log.trace("result: {}", result);
        log.debug("--> Out controller");
        return result;
    }

}
