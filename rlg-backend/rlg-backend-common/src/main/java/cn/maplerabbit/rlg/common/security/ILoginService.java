package cn.maplerabbit.rlg.common.security;

/**
 * 用户登录信息获取接口
 */
public interface ILoginService
{
    /**
     * 动态获取用户信息
     * @param field 数据库账户对应字段
     * @param account 账户名称
     * @return 用户信息
     */
    UserDetails loadUser(String field, String account);
}