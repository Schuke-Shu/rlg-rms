package cn.maplerabbit.rlg.module.user.service;

import cn.maplerabbit.rlg.entity.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户登录信息获取接口
 */
public interface ILoginInfoService
        extends UserDetailsService
{
    /**
     * 通过邮箱获取用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    UserDetails loadUserByEmail(String email);
}
