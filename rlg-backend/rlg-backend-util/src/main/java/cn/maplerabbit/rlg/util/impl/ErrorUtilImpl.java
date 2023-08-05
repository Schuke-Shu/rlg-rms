package cn.maplerabbit.rlg.util.impl;

import cn.maplerabbit.rlg.common.entity.ErrorDetail;
import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.exception.ProgramError;
import cn.maplerabbit.rlg.common.property.RlgProperties;
import cn.maplerabbit.rlg.common.util.IErrorUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.maplerabbit.rlg.common.enumpak.DirectoryEnum.ERROR_LOG;

/**
 * 异常工具实现类
 */
@Slf4j
@Component
public class ErrorUtilImpl implements IErrorUtil
{
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private RlgProperties rlgProperties;

    @Override
    public void response(ErrorResult result)
    {
        response.setContentType("application/json;charset=utf-8");

        try
        {
            PrintWriter writer = response.getWriter();

            writer.write(
                    JSON.toJSONString(
                            result
                    )
            );

            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            log.error("-- IOException, msg: {}", e.getMessage());
        }
    }

    @Override
    public void record(Class<?> from, Throwable error, Class<? extends ProgramError> thr)
    {
        // 获取异常信息
        Class<?> exception = error.getClass();
        String msg = error.getMessage();

        ErrorDetail errorDetail =
                new ErrorDetail(
                        from,
                        exception,
                        msg,
                        LocalDateTime.now(),
                        error
                );

        log.debug("ErrorDetail: {}", errorDetail);

        // 获取日志文件
        File file = errorLogFile();
        log.debug("Create error-log file: {}", file.getAbsolutePath());

        // 将对象写到异常日志中
        try (
                ObjectOutputStream oos = new ObjectOutputStream(
                        Files.newOutputStream(
                                file.toPath(),
                                // 开启追加
                                StandardOpenOption.APPEND
                        )
                )
        )
        {
            oos.writeObject(errorDetail);
            oos.flush();
        }
        catch (Exception e)
        {
            log.error("Failed to record exception: {}, msg: {}, error: {}", exception, error.getMessage(), e.getMessage());
            e.printStackTrace();
        }

        try
        {
            if (thr != null)
                throw thr.getConstructor(String.class).newInstance(error.getMessage());
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            log.error("Failed to throw custom exception -- {}", thr);
        }
    }

    private File errorLogFile()
    {
        // 获取异常日志文件夹
        File dir = new File(ERROR_LOG.router(rlgProperties.getFile().getRootDir()));
        if (!dir.exists() && !dir.mkdirs())
            throw new RuntimeException("Failed to create error directory: " + dir.getAbsolutePath());

        // 获取日志文件并返回
        File file = new File(dir, LocalDate.now().toString());
        try
        {
            if (!file.exists() && !file.createNewFile())
                throw new RuntimeException("Failed to create error file: " + file.getAbsolutePath());
            return new File(dir, LocalDate.now().toString());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}