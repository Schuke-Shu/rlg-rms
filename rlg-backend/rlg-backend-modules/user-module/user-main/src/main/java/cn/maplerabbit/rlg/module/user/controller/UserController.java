package cn.maplerabbit.rlg.module.user.controller;

import cn.maplerabbit.rlg.common.constpak.ValidationMessageConst;
import cn.maplerabbit.rlg.common.security.UserDetails;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.user.dto.UserLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.common.entity.result.JsonResult;
import cn.maplerabbit.rlg.common.entity.result.SuccessResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public JsonResult<?> login(
            UserLoginDTO userLoginDTO,
            @ApiIgnore @AuthenticationPrincipal UserDetails details
    )
    {
        return SuccessResult.ok(userService.login(details));
    }

    @ApiOperation("刷新JWT")
    @GetMapping("/refresh")
    public JsonResult<?> refresh(HttpServletRequest request)
    {
        return SuccessResult.ok(userService.refresh(request.getHeader(AUTHORIZATION_HEADER)));
    }
}