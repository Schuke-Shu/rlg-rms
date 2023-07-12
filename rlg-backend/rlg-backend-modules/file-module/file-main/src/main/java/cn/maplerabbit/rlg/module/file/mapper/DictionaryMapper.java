package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.pojo.file.entity.Dictionary;
import cn.maplerabbit.rlg.common.template.LongModelMapperTemplate;

import java.util.List;

public interface DictionaryMapper
        extends LongModelMapperTemplate<Dictionary>
{
    List<Dictionary> listByParentId(Long id);
}