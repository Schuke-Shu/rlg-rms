package cn.maplerabbit.rlg.module.user.service;

import cn.maplerabbit.rlg.pojo.user.dto.UserEmailLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UsernameLoginDTO;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;
import cn.maplerabbit.rlg.pojo.user.vo.UserInfoVO;

/**
 * 用户服务接口
 */
public interface IUserService
{
    /**
     * 普通用户角色
     */
    String ROLE_USER = "ROLE_USER";

    /**
     * 用户名注册
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户名登录
     * @param userLoginDTO 用户名登录的dto类
     * @return jwt token
     */
    String login(UsernameLoginDTO userLoginDTO);

    /**
     * 获取邮箱登录验证码
     * @param email 邮箱
     */
    void getEmailLoginCode(String email);

    /**
     * 用户邮箱登录
     * @param userEmailLoginDTO email登录信息
     * @return jwt token
     */
    String emailLogin(UserEmailLoginDTO userEmailLoginDTO);

    /**
     * 刷新jwt
     * @param jwt 请求头中旧的jwt
     * @return 新的jwt
     */
    String refresh(String jwt);

    /**
     * 通过jwt获取用户信息
     * @param uuid 用户uuid
     * @return 用户信息vo类
     */
    UserInfoVO getUserInfo(String uuid);
}
