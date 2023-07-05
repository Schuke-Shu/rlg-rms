package cn.maplerabbit.rlg.module.user.mapper;

import cn.maplerabbit.rlg.pojo.user.entity.User;
import cn.maplerabbit.rlg.pojo.user.vo.UserLoginVO;
import cn.maplerabbit.rlg.common.template.StringModelMapperTemplate;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface UserMapper extends StringModelMapperTemplate<User>
{
    /**
     * 更新登录时间和ip
     * @param uuid  用户uuid
     * @param time  登陆时间
     * @param ip    登录ip
     * @return 更新数据数量
     */
    int updateLogin(
            @Param("uuid") String uuid,
            @Param("time") LocalDateTime time,
            @Param("ip") String ip
    );

    /**
     * 根据指定字段获取登录信息
     * @param field 字段名称
     * @param account 指定账户
     * @return 登录信息
     */
    UserLoginVO loadUserByCustomField(
            @Param("field") String field,
            @Param("account") String account
    );

    /**
     * 根据指定字段获取用户数
     * @param field 字段名称
     * @param account 指定账户
     * @return 用户数
     */
    int countByCustomField(
            @Param("field") String field,
            @Param("account") String account
    );
}