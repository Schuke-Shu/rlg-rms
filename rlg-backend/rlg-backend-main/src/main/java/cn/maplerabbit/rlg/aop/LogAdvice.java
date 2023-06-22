package cn.maplerabbit.rlg.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class LogAdvice
{
    @Autowired
    HttpServletRequest request;

    public LogAdvice()
    {
        log.debug("LogAspect()...");
    }

    @Around("execution(* cn.maplerabbit.rlg.module.*.controller.*.*(..))")
    public Object log(ProceedingJoinPoint pjp) throws Throwable
    {
        log.debug(
                "Get Request:\ntime: {}\nContent-type: {}\nip: {}\nrequestPath: {}",
                LocalDateTime.now(),
                request.getHeader("content-type"),
                request.getRemoteAddr(),
                request.getRequestURI()
        );
        log.debug(
                "--> class: {}, method: {}",
                pjp.getTarget().getClass().getName(),
                pjp.getSignature().getName()
        );
        log.trace("args: {}", pjp.getArgs());

        Object result = pjp.proceed();

        log.trace("result: {}", result);
        log.debug("--> out");
        return result;
    }

}
