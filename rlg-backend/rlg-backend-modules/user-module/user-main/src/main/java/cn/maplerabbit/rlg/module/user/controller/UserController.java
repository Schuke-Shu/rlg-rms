package cn.maplerabbit.rlg.module.user.controller;

import cn.maplerabbit.rlg.module.user.entity.User;
import cn.maplerabbit.rlg.module.user.mapper.UserMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户模块")
public class UserController
{
    @Autowired
    private UserMapper mapper;

    public UserController()
    {
        log.trace("UserController()...");
    }

    @GetMapping("/hello")
    public User hello()
    {
        return mapper.query(1L);
    }
}