package cn.maplerabbit.rlg.module.user.service;

import cn.maplerabbit.rlg.common.security.ILoginRegisterService;
import cn.maplerabbit.rlg.common.security.UserDetails;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;

/**
 * 用户服务接口
 */
public interface IUserService extends ILoginRegisterService
{
    /**
     * 用户名注册
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户名登录
     * @param userLoginDTO 用户名登录的dto类
     * @return jwt token
     */
    String login(UserDetails details);

    /**
     * 刷新jwt
     * @param jwt 请求头中旧的jwt
     * @return 新的jwt
     */
    String refresh(String jwt);
}
