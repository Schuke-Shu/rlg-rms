package cn.maplerabbit.rlg.module.user.controller;

import cn.maplerabbit.rlg.common.constpak.ValidationMessageConst;
import cn.maplerabbit.rlg.common.entity.UserDetails;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.common.entity.result.JsonResult;
import cn.maplerabbit.rlg.common.entity.result.SuccessResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequestMapping(value = "/user")
@Api(tags = "01. 用户模块")
public class UserController
        implements ValidationMessageConst
{
    /**
     * 包含权限的请求头名称
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private IUserService userService;

    public UserController()
    {
        log.debug("UserController()...");
    }

    @ApiOperation("用户名注册")
    @PostMapping("/register")
    public JsonResult<?> register(@Valid UserRegisterDTO userRegisterDTO)
    {
        userService.register(userRegisterDTO);
        return SuccessResult.ok();
    }

    private static final String LOGIN_API_NOTE = "必须携带请求头login-way，值为pwd或code，表明是密码登录或验证码登录";

    @ApiOperation(value = "用户登录", notes = LOGIN_API_NOTE)
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "账户名称（用户名/邮箱/手机号）", name = "account", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(value = "密钥（密码/验证码）", name = "key", required = true, dataTypeClass = String.class)
    })
    public JsonResult<?> login(@ApiIgnore @AuthenticationPrincipal UserDetails details)
    {
        return SuccessResult.ok(userService.login(details));
    }

    @ApiOperation(value = "刷新JWT")
    @GetMapping("/token/refresh")
    public JsonResult<?> refresh(HttpServletRequest request)
    {
        return SuccessResult.ok(
                userService.refresh(
                        request.getHeader(AUTHORIZATION_HEADER)
                )
        );
    }
}