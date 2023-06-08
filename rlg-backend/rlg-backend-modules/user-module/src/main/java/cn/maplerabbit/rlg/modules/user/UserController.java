package cn.maplerabbit.rlg.modules.user;

import lombok.extern.slf4j.Slf4j;
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