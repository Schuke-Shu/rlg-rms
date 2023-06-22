package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.pojo.user.entity.Role;
import cn.maplerabbit.rlg.template.LongTemplate;

public interface RoleMapper extends LongTemplate<Role>
{
    Role queryByFlag(String role);
}