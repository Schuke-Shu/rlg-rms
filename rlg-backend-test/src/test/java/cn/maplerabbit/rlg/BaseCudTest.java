package cn.maplerabbit.rlg;

import cn.maplerabbit.rlg.common.entity.AssociateUnit;
import cn.maplerabbit.rlg.common.template.AssociateMapperTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 关联表CUD操作测试接口
 *
 * @param <L> 左数据主键类型
 * @param <R> 右数据主键类型
 * @param <M> mapper类型
 */
@SpringBootTest
public interface BaseCudTest<L extends Serializable, R extends Serializable, M extends AssociateMapperTemplate<L, R>>
{
    /**
     * 获取mapper
     *
     * @return mapper
     */
    M mapper();

    /**
     * 自动测试，需要在这个方法上添加{@link Test}注解
     */
    void baseCrudTest()
            throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * 自动测试
     *
     * @param params 参数，不得少于三个
     * @throws IllegalArgumentException 如果参数少于三个
     */
    @SuppressWarnings("ALL")
    default void baseCrudTest(AssociateUnit update, AssociateUnit... params)
            throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        if (params == null || params.length < 3) throw new IllegalArgumentException("自动测试参数不得少于三个");

        M mapper = mapper();
        assert mapper.save(params[0]) == 1;

        List<AssociateUnit<L, R>> list = new ArrayList<>(
                Arrays
                        .asList(params)
                        .subList(1, params.length)
        );

        assert mapper.saveBatch(list) == list.size();

        update.setId(params[0].getId());

        assert mapper.update(update) == 1;

        List<Long> ids = new ArrayList<>();
        for (int i = 1; i < params.length; i++)
            ids.add(params[i].getId());

        assert mapper.count() >= params.length;

        assert mapper.remove(update.getId()) == 1;

        assert mapper.removeBatch(ids) == ids.size();
    }
}