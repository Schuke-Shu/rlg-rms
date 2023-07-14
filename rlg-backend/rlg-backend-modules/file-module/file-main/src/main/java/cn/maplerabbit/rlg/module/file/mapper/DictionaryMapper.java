package cn.maplerabbit.rlg.module.file.mapper;

import cn.maplerabbit.rlg.pojo.file.entity.Dictionary;
import cn.maplerabbit.rlg.common.template.LongModelMapperTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryMapper
        extends LongModelMapperTemplate<Dictionary>
{
    /**
     * 通过父目录id列出文件列表
     * @param id 父目录id
     * @return 文件列表
     */
    List<Dictionary> listByParentId(
            @Param("userUuid") String userUuid,
            @Param("parentId") Long parentId
    );

    /**
     * 通过父目录id与文件名查询文件个数，一般用于文件名查重
     *
     * @param parentId 父目录id
     * @param filename 文件名
     * @return 同一父目录下的同名文件个数
     */
    Dictionary queryByParentIdAndFilename(
            @Param("userUuid") String userUuid,
            @Param("parentId") Long parentId,
            @Param("filename") String filename
    );
}