package cn.maplerabbit.rlg.module.user.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户模块")
public class UserController
{
    public UserController()
    {
        log.trace("UserController()...");
    }
}