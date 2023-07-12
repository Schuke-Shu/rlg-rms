package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.pojo.file.entity.File;
import cn.maplerabbit.rlg.common.template.StringModelMapperTemplate;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends StringModelMapperTemplate<File>
{
    File queryBySha512AndSize(
            @Param("sha512") String sha512,
            @Param("size") Long size
    );
}