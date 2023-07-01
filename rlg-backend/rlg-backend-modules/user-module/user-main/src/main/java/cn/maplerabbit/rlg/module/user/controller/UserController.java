package cn.maplerabbit.rlg.module.user.controller;

import cn.maplerabbit.rlg.constpak.ValidationMessageConst;
import cn.maplerabbit.rlg.entity.LoginPrincipal;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.user.dto.UserEmailLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UsernameLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.util.ValidationCodeUtil;
import cn.maplerabbit.rlg.entity.result.JsonResult;
import cn.maplerabbit.rlg.entity.result.SuccessResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    @Autowired
    private ValidationCodeUtil validationCodeUtil;

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

    @ApiOperation("用户名登录")
    @PostMapping("/login")
    public JsonResult<?> login(@Valid UsernameLoginDTO userLoginDTO) {
        return SuccessResult.ok(userService.login(userLoginDTO));
    }

    @ApiOperation("获取邮箱登录验证码")
    @GetMapping("/email-login")
    @ApiImplicitParam(name = "email", value = "登录邮箱", required = true, dataType = "string")
    public JsonResult<?> getEmailLoginCode(@NotBlank(message = USER_EMAIL) @Email(message = USER_EMAIL) String email)
    {
        userService.getEmailLoginCode(email);
        return SuccessResult.ok();
    }

    @ApiOperation("用户邮箱登录")
    @PostMapping("/email-login")
    public JsonResult<?> emailLogin(@Valid UserEmailLoginDTO userEmailLoginDTO)
    {
        return SuccessResult.ok(userService.emailLogin(userEmailLoginDTO));
    }

    @ApiOperation("刷新JWT")
    @GetMapping("/refresh")
    public JsonResult<?> refresh(HttpServletRequest request)
    {
        return SuccessResult.ok(userService.refresh(request.getHeader(AUTHORIZATION_HEADER)));
    }

    @ApiOperation("通过jwt获取用户信息")
    @GetMapping("/user-info")
    public JsonResult<?> getUserInfo(@ApiIgnore @AuthenticationPrincipal LoginPrincipal principal)
    {
        return SuccessResult.ok(userService.getUserInfo(principal.getUuid()));
    }
}