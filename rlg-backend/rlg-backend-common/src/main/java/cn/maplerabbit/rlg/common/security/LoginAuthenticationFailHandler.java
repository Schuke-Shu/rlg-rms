package cn.maplerabbit.rlg.common.security;

import cn.maplerabbit.rlg.common.entity.result.ErrorResult;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.maplerabbit.rlg.common.util.FilterError.error;

@Slf4j
@Component
public class LoginAuthenticationFailHandler
        implements AuthenticationFailureHandler
{
    public LoginAuthenticationFailHandler() {log.debug("LoginAuthenticationFailHandler()...");}

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    )
            throws IOException, ServletException
    {
        error(
                response,
                ErrorResult.fail(
                        ServiceCode.ERR_UNAUTHORIZED,
                        exception.getMessage()
                )
        );
    }
}