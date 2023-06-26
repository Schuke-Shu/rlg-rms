package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.pojo.user.entity.User;
import cn.maplerabbit.rlg.pojo.user.vo.UserLoginVO;
import cn.maplerabbit.rlg.template.LongTemplate;

public interface UserMapper extends LongTemplate<User>
{
    /**
     * 根据用户名获取登录信息
     * @param username 用户名
     * @return 登录信息
     */
    UserLoginVO getLoginInfoByUserName(String username);

    /**
     * 根据邮箱获取登录信息
     * @param email 邮箱
     * @return 登录信息
     */
    UserLoginVO getLoginInfoByEmail(String email);

    /**
     * 根据手机号获取登录信息
     * @param phone 手机号
     * @return 登录信息
     */
    UserLoginVO getLoginInfoByPhone(String phone);

    /**
     * 根据用户名获取用户数
     * @param username 用户名
     * @return 用户数
     */
    int countByUsername(String username);

    /**
     * 根据邮箱获取用户数
     * @param email 邮箱
     * @return 用户数
     */
    int countByEmail(String email);

    /**
     * 根据手机号获取用户数
     * @param phone 手机号
     * @return 用户数
     */
    int countByPhone(String phone);
}