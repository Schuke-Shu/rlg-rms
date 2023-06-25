package cn.maplerabbit.rlg.module.user.service;

import cn.maplerabbit.rlg.pojo.user.dto.UserLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.pojo.user.vo.UserInfoVO;

/**
 * 用户服务接口
 */
public interface IUserService
{
    /**
     * 用户注册
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     *
     * @return jwt token
     */
    String login(UserLoginDTO userLoginDTO);

    /**
     * 刷新jwt
     */
    String refresh(String header);

    UserInfoVO getUserInfo(Long id);
}
