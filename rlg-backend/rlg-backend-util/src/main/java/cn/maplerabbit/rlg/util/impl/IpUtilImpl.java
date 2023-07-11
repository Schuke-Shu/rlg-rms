package cn.maplerabbit.rlg.util.impl;

import cn.maplerabbit.rlg.common.util.IIpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class IpUtilImpl implements IIpUtil
{
    @Autowired
    private HttpServletRequest request;

    /**
     * 可能包含ip的请求头
     */
    private static final String[] HEADERS = {
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
            "X-Real-IP"
    };

    public IpUtilImpl() {log.debug("IpUtilImpl()...");}

    /**
     * 获取ip
     */
    public String ip()
    {
        String ip = request.getHeader("x-forwarded-for");

        if (!isEmpty(ip))
            if (ip.contains(","))
                ip = ip.split(",")[0];

        for (String header : HEADERS)
            if (isEmpty(ip))
                ip = request.getHeader(header);

        if (isEmpty(ip)) ip = request.getRemoteAddr();

        return ip;
    }

    private static boolean isEmpty(String ip)
    {
        return !StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip);
    }
}