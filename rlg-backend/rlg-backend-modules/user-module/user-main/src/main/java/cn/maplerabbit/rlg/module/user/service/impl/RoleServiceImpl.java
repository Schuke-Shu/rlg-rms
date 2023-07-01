package cn.maplerabbit.rlg.module.user.service.impl;

import cn.maplerabbit.rlg.common.entity.AssociateUnit;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.exception.RoleException;
import cn.maplerabbit.rlg.module.user.mapper.RoleMapper;
import cn.maplerabbit.rlg.module.user.mapper.UserRoleMapper;
import cn.maplerabbit.rlg.module.user.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleServiceImpl implements IRoleService
{
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void bindDefaultRole(String uuid)
    {
        Long id = roleMapper.queryByFlag(ROLE_USER).getId();

        if (id == null)
        {
            log.error("The data 'ROLE_USER' of table role has lost");
            throw new RuntimeException("The data 'ROLE_USER' in table role has lost");
        }

        if (
                userRoleMapper.save(
                        new AssociateUnit<>(uuid, id)
                )
                != 1
        )
        {
            log.error("Bind role failed, user uuid: {}", uuid);
            throw new RoleException(ServiceCode.ERR_INSERT, "注册失败，请稍后再试");
        }
    }
}