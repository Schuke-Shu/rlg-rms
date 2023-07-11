package cn.maplerabbit.rlg.module.user.service;

import cn.maplerabbit.rlg.common.entity.UserDetails;
import cn.maplerabbit.rlg.common.exception.ProgramError;
import cn.maplerabbit.rlg.pojo.user.dto.UserRegisterDTO;

/**
 * 用户服务接口
 */
public interface IUserService
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
    String refresh(String jwt)
            throws ProgramError;

    /**
     * 动态获取用户信息
     * @param field 数据库账户对应字段
     * @param account 账户名称
     * @return 用户信息
     */
    UserDetails loadUser(String field, String account);
}
