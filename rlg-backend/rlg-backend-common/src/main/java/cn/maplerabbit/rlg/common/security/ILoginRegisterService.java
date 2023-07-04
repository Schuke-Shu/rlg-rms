package cn.maplerabbit.rlg.common.security;

/**
 * 用户登录信息获取接口
 */
public interface ILoginRegisterService
{
    /**
     * 通过邮箱获取用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    UserDetails loadUser(String field, String account);
}