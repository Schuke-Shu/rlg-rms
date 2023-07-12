package cn.maplerabbit.rlg.filter;

import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.entity.result.SuccessResult;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.property.RlgProperties;
import cn.maplerabbit.rlg.common.util.*;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 验证码获取，参数为account，可能为邮箱或手机号
 */
@Slf4j
public class CodeObtainFilter
        extends HttpFilter
{
    /**
     * 匹配 "/code" 开头的路径
     */
    private static final AntPathRequestMatcher MATCHER = new AntPathRequestMatcher("/code/**", "GET");

    private static final String URI_PREFIX_CODE = "/code";

    /**
     * 请求参数名称
     */
    private static final String ACCOUNT_PARAM = "account";

    private ICodeUtil codeUtil;
    private IAccountUtil accountUtil;
    private IRedisUtil<String> redisUtil;
    private IErrorUtil errorUtil;
    private RlgProperties rlgProperties;

    public CodeObtainFilter()
    {
        log.debug("CodeObtainFilter()...");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        log.trace("Access CodeObtainFilter");

        if (!MATCHER.matches(request))
            chain.doFilter(request, response);
        else
            sendCode(request, response);
    }

    private void sendCode(HttpServletRequest request, HttpServletResponse response)
    {
        String account = request.getParameter(ACCOUNT_PARAM);

        if (!StringUtils.hasText(account))
        {
            log.debug("Account is empty");
            errorUtil.response(
                    ErrorResult.fail(ServiceCode.ERR_BAD_REQUEST, "账户不能为空")
            );
            return;
        }

        // 获取验证码
        String code = codeUtil.generate();
        log.debug("Code: {}", code);

        // 存储验证码
        redisUtil.set(
                redisUtil.key(
                        request.getRequestURI()
                                .substring(URI_PREFIX_CODE.length()),
                        account
                ),
                code,
                rlgProperties
                        .getCode()
                        .getUsableTime()
        );

        // 发送验证码
        IAccountUtil.CodeSender sender = accountUtil.getSender(account);
        if (sender == null)
        {
            errorUtil.response(
                    ErrorResult.fail(ServiceCode.ERR_BAD_REQUEST, "账户格式不正确")
            );
            return;
        }

        sender.send(account, code);

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
        }
    }

    public CodeObtainFilter setCodeUtil(ICodeUtil codeUtil)
    {
        this.codeUtil = codeUtil;
        return this;
    }

    public CodeObtainFilter setAccountUtil(IAccountUtil accountUtil)
    {
        this.accountUtil = accountUtil;
        return this;
    }

    public CodeObtainFilter setRedisUtil(IRedisUtil<String> redisUtil)
    {
        this.redisUtil = redisUtil;
        return this;
    }

    public CodeObtainFilter setErrorUtil(IErrorUtil errorUtil)
    {
        this.errorUtil = errorUtil;
        return this;
    }

    public CodeObtainFilter setRlgProperties(RlgProperties rlgProperties)
    {
        this.rlgProperties = rlgProperties;
        return this;
    }
}