package cn.maplerabbit.rlg.module.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements IUserService
{
    public UserServiceImpl()
    {
        log.trace("UserServiceImpl()...");
    }
}