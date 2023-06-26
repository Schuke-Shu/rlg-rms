package cn.maplerabbit.rlg.util;


import javax.servlet.http.HttpServletRequest;

public class IpUtil
{
    public static String getIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (!isEmpty(ip))
            if (ip.contains(","))
                ip = ip.split(",")[0];

        if (isEmpty(ip)) ip = request.getHeader("Proxy-Client-IP");
        if (isEmpty(ip)) ip = request.getHeader("WL-Proxy-Client-IP");
        if (isEmpty(ip)) ip = request.getHeader("HTTP_CLIENT_IP");
        if (isEmpty(ip)) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (isEmpty(ip)) ip = request.getHeader("X-Real-IP");
        if (isEmpty(ip)) ip = request.getRemoteAddr();

        return ip;
    }

    private static boolean isEmpty(String ip)
    {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }
}