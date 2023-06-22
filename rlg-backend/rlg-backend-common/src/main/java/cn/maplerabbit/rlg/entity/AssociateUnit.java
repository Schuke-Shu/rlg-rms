package cn.maplerabbit.rlg.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 关联单元，用于关联表数据的增删改
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class AssociateUnit<L, R>
        implements Serializable
{
    private Long id;
    /**
     * 左数据
     */
    private L left;
    /**
     * 右数据
     */
    private R right;

    public AssociateUnit(L l, R r)
    {
        this.left = l;
        this.right = r;
    }

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("id=").append(id)
                .append(", left=").append(left)
                .append(", right=").append(right)
                .append('}')
                .toString();
    }
}