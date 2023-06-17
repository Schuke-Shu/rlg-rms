package cn.maplerabbit.rlg.template;

import cn.maplerabbit.rlg.entity.AssociateUnit;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 关联表映射类的增删改模板
 * @param <X> 关联表左数据
 * @param <Y> 关联表右数据
 */
public interface AssociateMapperTemplate<X extends Serializable, Y extends Serializable>
{
    /**
     * 单个添加
     * @return 受影响行数
     */
    int save(AssociateUnit<X, Y> unit);

    /**
     * 批量添加
     * @return 受影响行数
     */
    int saveBatch(List<AssociateUnit<X, Y>> list);

    /**
     * 单个删除
     * @param pk primary key
     * @return 受影响行数
     */
    int remove(@Param("pk") Long pk);

    /**
     * 批量删除
     * @param pks primary keys
     * @return 受影响行数
     */
    int removeBatch(List<Long> pks);

    /**
     * 单个更新
     * @return 受影响行数
     */
    int update(AssociateUnit<X, Y> unit);

    /**
     * 统计
     */
    int count();
}
