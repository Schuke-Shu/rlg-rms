package cn.maplerabbit.rlg.controller;

import cn.maplerabbit.rlg.generator.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController
{
    public UserController()
    {
        log.trace("UserController()...");
    }
}