package cn.maplerabbit.rlg.aop;

import cn.maplerabbit.rlg.common.util.IIpUtil;
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
    private HttpServletRequest request;
    @Autowired
    private IIpUtil ipUtil;

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
        log.trace("--> Enter controller: {}", pjp.getTarget().getClass().getSimpleName());
        log.debug(
                "Get Request:\ntime: {}\nContent-type: {}\nip: {}\nmethod: {}\ntarget: {}",
                LocalDateTime
                        .now()
                        .toString()
                        .replace('T', ' '),
                request.getHeader("content-type"),
                ipUtil.ip(),
                request.getMethod(),
                request.getRequestURI()
        );
        log.trace(
                "Controller method --> {}",
                pjp.getSignature()
        );
        log.debug("args: {}", pjp.getArgs());

        Object result = pjp.proceed();

        log.debug("result: {}", result);
        log.trace("--> Out controller");
        return result;
    }

    /**
     * 匹配项目中所有类的所有方法，记录跟踪日志
     */
    @Around("execution(* cn.maplerabbit.rlg..*(..))")
    public Object logEntryAndExit(ProceedingJoinPoint pjp)
            throws Throwable
    {
        log.trace("--> Enter: {}", pjp.getSignature());
        Object result = pjp.proceed();
        log.trace("--> Out: {}", pjp.getSignature());
        return result;
    }
}
