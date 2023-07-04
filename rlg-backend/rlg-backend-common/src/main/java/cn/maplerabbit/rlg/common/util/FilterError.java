package cn.maplerabbit.rlg.common.util;

import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用于在过滤器中向客户端返回错误信息
 */
@Slf4j
public class FilterError
{

    /**
     * 向客户端返回异常信息
     */
    public static void error(HttpServletResponse response, ErrorResult result)
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
            throw new RuntimeException(e.getMessage());
        }
    }
}