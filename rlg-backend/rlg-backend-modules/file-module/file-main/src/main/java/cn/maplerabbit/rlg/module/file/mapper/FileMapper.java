package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.pojo.file.entity.File;
import cn.maplerabbit.rlg.common.template.StringModelMapperTemplate;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends StringModelMapperTemplate<File>
{
    /**
     * 通过sha512与文件大小查询文件索引，一般用于文件查重
     * @param sha512 文件sha512值
     * @param size 文件大小
     * @return 文件
     */
    File queryBySha512AndSize(
            @Param("sha512") String sha512,
            @Param("size") Long size
    );
}