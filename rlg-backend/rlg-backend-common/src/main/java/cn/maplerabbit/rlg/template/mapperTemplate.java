package cn.maplerabbit.rlg.template;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * mapper类的基础增删改查模板
 * @param <T> 被操作对象
 */
public interface mapperTemplate<T>
{
    /**
     * 单个添加
     * @return 受影响行数
     */
    int save(T t);

    /**
     * 批量添加
     * @return 受影响行数
     */
    int saveBatch(List<T> list);

    /**
     * 单个删除
     * @param pk primary key
     * @return 受影响行数
     */
    int remove(@Param("pk") Serializable pk);

    /**
     * 批量删除
     * @param pks primary keys
     * @return 受影响行数
     */
    int removeBatch(List<Serializable> pks);

    /**
     * 单个更新
     * @return 受影响行数
     */
    int update(T t);

    /**
     * 单个查询
     * @param pk primary key
     */
    T query(@Param("pk") Serializable pk);

    /**
     * 批量查询
     * @param pks primary keys
     */
    List<T> queryBatch(List<Serializable> pks);
}
