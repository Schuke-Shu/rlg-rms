package cn.maplerabbit.rlg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 关联单元，用于批量增删关联表的数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AssociateUnit<X extends Serializable, Y extends Serializable> implements Serializable
{
    private Long id;
    /**
     * 左数据
     */
    private X x;
    /**
     * 右数据
     */
    private Y y;

    @Override
    public String toString()
    {
        return new StringJoiner(", ", AssociateUnit.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("x=" + x)
                .add("y=" + y)
                .toString();
    }
}