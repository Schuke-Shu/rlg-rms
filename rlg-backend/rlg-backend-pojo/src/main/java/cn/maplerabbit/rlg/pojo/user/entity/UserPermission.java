package cn.maplerabbit.rlg.pojo.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserPermission implements Serializable
{
    private Long id;
    /**
     * 用户权限唯一标识符
     */
    private String flag;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime modifiedTime;

    @Override
    public String toString()
    {
        return new StringBuilder("UserPermission{")
                .append("id=").append(id)
                .append(", flag='").append(flag).append('\'')
                .append(", description='").append(description).append('\'')
                .append(", createTime=").append(createTime)
                .append(", modifiedTime=").append(modifiedTime)
                .append('}')
                .toString();
    }
}