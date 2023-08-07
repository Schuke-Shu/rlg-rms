package cn.maplerabbit.rlg;

import cn.maplerabbit.rlg.common.template.ModelMapperTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mapper CRUD操作测试接口
 *
 * @param <T> 存储数据类型
 * @param <K> 主键类型
 * @param <M> mapper类型
 */
@SpringBootTest
public interface BaseCrudTest<T, K extends Serializable, M extends ModelMapperTemplate<T, K>>
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
    default void baseCrudTest(String pkName, T update, T... params)
            throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        if (params == null || params.length < 3) throw new IllegalArgumentException("自动测试参数不得少于三个");

        M mapper = mapper();
        assert mapper.save(params[0]) == 1;

        List<T> list = new ArrayList<>(
                Arrays
                        .asList(params)
                        .subList(1, params.length)
        );

        assert mapper.saveBatch(list) == list.size();



        // 参数类型
        Class<?> type = update.getClass();
        // 主键类型
        Class<?> kt = type.getDeclaredField(pkName).getType();
        // 获取id方法
        Method getId = type.getDeclaredMethod(
                "get" + Character.toUpperCase(pkName.charAt(0)) + new String(pkName.substring(1))
        );
        // 设置id方法
        Method setId = type.getDeclaredMethod(
                "set" + Character.toUpperCase(pkName.charAt(0)) + new String(pkName.substring(1)),
                kt
        );

        K p = (K) getId.invoke(params[0]);
        setId.invoke(update, p);

        assert mapper.update(update) == 1;

        assert mapper.query(p) != null;

        List<K> ids = new ArrayList<>();
        for (int i = 1; i < params.length; i++)
            ids.add(
                    (K) getId.invoke(params[i])
            );
        List<T> ls = mapper.queryBatch(ids);
        assert ls.size() == ids.size();
        for (T t : ls)
            assert t != null;

        assert mapper.count() >= params.length;

        assert mapper.remove(p) == 1;

        assert mapper.removeBatch(ids) == ids.size();
    }
}