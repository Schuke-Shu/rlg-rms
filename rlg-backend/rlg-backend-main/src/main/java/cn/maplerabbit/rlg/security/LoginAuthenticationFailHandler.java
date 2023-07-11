package cn.maplerabbit.rlg.security;

import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.util.IErrorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginAuthenticationFailHandler
        implements AuthenticationFailureHandler
{
    @Autowired
    private IErrorUtil errorUtil;

    public LoginAuthenticationFailHandler() {log.debug("LoginAuthenticationFailHandler()...");}

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    )
            throws IOException, ServletException
    {
        log.debug("Access LoginAuthenticationFailHandler onAuthenticationFailure()");
        log.debug("-- {}, msg: {}", exception.getClass().getSimpleName(), exception.getMessage());
        errorUtil.response(
                ErrorResult.fail(ServiceCode.ERR_UNAUTHORIZED, exception.getMessage())
        );
    }
}