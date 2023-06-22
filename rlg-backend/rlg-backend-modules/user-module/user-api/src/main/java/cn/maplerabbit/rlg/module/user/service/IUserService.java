package cn.maplerabbit.rlg.module.user.service;

import cn.maplerabbit.rlg.pojo.user.dto.UserLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;

public interface IUserService
{
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @return jwt token
     */
    String login(UserLoginDTO userLoginDTO);

    String refresh(String header);
}
