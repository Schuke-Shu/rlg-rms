package cn.maplerabbit.rlg.module.file.service;

import cn.maplerabbit.rlg.common.entity.LoginPrincipal;
import cn.maplerabbit.rlg.pojo.file.dto.MkdirDTO;
import cn.maplerabbit.rlg.pojo.file.vo.ListFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件索引服务接口
 */
public interface IDictionaryService
{
    /**
     * 没有父目录时，deep与parentId的值
     */
    Integer NO_PARENT = 0;
    /**
     * 是目录
     */
    Integer IS_DIRECTORY = 1;
    /**
     * 不是目录
     */
    Integer NOT_DIRECTORY = 0;
    /**
     * 在回收站中
     */
    Integer IS_DELETED = 1;
    /**
     * 不在回收站中
     */
    Integer NOT_DELETED = 0;
    /**
     * 隐藏
     */
    Integer IS_HIDDEN = 1;
    /**
     * 不隐藏
     */
    Integer NOT_HIDDEN = 0;
    /**
     * 根路径
     */
    String ROOT = "/";
    /**
     * 一天的毫秒数
     */
    Long ONE_DAY_MILLIS = 0b101001001100101110000000000L;

    /**
     * 创建文件夹
     * @param mkdirDTO 创建文件夹DTO
     * @param principal 当事人
     */
    void mkdir(MkdirDTO mkdirDTO, LoginPrincipal principal);

    /**
     * 上传文件
     * @param parentId 父目录id
     * @param file 文件
     * @param principal 当事人
     */
    void upload(Long parentId, MultipartFile file, LoginPrincipal principal);

    /**
     * 根据父目录id查询文件列表
     *
     * @param path
     * @param principal 当事人
     * @return 文件列表
     */
    List<ListFileVO> list(String path, LoginPrincipal principal);
}
