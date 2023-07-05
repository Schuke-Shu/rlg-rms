package cn.maplerabbit.rlg.filter;

import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.entity.result.SuccessResult;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.util.ValidationCodeUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static cn.maplerabbit.rlg.common.util.FilterError.error;

/**
 * 验证码获取，参数为account，可能为邮箱或手机号
 */
@Slf4j
@Component
public class CodeObtainFilter extends HttpFilter
{
    /**
     * 匹配code开头的路径
     */
    private static final AntPathRequestMatcher MATCHER = new AntPathRequestMatcher("/code/**", "GET");

    /**
     * 请求参数名称
     */
    private static final String ACCOUNT_PARAM = "account";

    @Autowired
    private ValidationCodeUtil validationCodeUtil;

    public CodeObtainFilter() {log.debug("CodeObtainFilter()...");}

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        log.trace(
                "Entry LoginAuthenticationFilter, details: \nrequest method: {}\nuri: {}",
                request.getMethod(),
                request.getRequestURI()
        );
        
        if (!MATCHER.matches(request))
            chain.doFilter(request, response);
        else
            sendCode(request, response);
    }

    private void sendCode(HttpServletRequest request, HttpServletResponse response)
    {
        log.debug("Access CodeObtainFilter");

        String account = request.getParameter(ACCOUNT_PARAM);
        if (!StringUtils.hasText(account))
        {
            error(
                    response,
                    ErrorResult.fail(ServiceCode.ERR_BAD_REQUEST, "账户不能为空")
            );
            return;
        }

        validationCodeUtil.send(account);

        response.setContentType("application/json;charset=utf-8");

        try
        {
            PrintWriter writer = response.getWriter();

            writer.write(
                    JSON.toJSONString(
                            SuccessResult.ok()
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