package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.pojo.user.entity.User;
import cn.maplerabbit.rlg.pojo.user.vo.UserLoginVO;
import cn.maplerabbit.rlg.template.LongTemplate;

public interface UserMapper extends LongTemplate<User>
{
    UserLoginVO getLoginInfoByUserName(String username);

    int countByUsername(String username);
}