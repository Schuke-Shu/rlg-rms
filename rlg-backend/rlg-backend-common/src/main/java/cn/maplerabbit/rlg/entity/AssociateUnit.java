package cn.maplerabbit.rlg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 关联单元，用于关联表数据的增删改
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AssociateUnit<L extends Serializable, R extends Serializable> implements Serializable
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

    @Override
    public String toString()
    {
        return new StringJoiner(", ", AssociateUnit.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("left=" + left)
                .add("right=" + right)
                .toString();
    }
}