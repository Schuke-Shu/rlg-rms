package cn.maplerabbit.rlg.module.user.controller;

import cn.maplerabbit.rlg.entity.LoginPrincipal;
import cn.maplerabbit.rlg.module.user.service.IUserService;
import cn.maplerabbit.rlg.pojo.user.dto.UserLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.web.JsonResult;
import cn.maplerabbit.rlg.web.SuccessResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@Api(tags = "01. 用户模块")
public class UserController
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

    @ApiOperation("用户名登录")
    @PostMapping(value = "/login")
    public JsonResult<?> login(@Valid UserLoginDTO userLoginDTO) {
        return SuccessResult.ok(userService.login(userLoginDTO));
    }

    @ApiOperation("刷新JWT")
    @GetMapping("/refresh")
    public JsonResult<?> refresh(HttpServletRequest request)
    {
        return SuccessResult.ok(userService.refresh(request.getHeader(AUTHORIZATION_HEADER)));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/user-info")
    public JsonResult<?> getUserInfo(@ApiIgnore @AuthenticationPrincipal LoginPrincipal principal)
    {
        return SuccessResult.ok(userService.getUserInfo(principal.getId()));
    }
}