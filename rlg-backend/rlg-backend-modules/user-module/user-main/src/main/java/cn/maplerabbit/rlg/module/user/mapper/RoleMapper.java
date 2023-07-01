package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.pojo.user.entity.Role;
import cn.maplerabbit.rlg.common.template.LongModelMapperTemplate;

public interface RoleMapper extends LongModelMapperTemplate<Role>
{
    Role queryByFlag(String role);
}