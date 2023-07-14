package cn.maplerabbit.rlg.module.file.service;

import cn.maplerabbit.rlg.pojo.file.entity.File;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件实体服务接口
 */
public interface IFileService
{
    /**
     * 通过sha512与文件大小查询Ftp是否存在某个文件
     * @param sha512 文件sha512值
     * @param size 文件大小
     * @return 文件
     */
    File ftpExist(String sha512, Long size);

    /**
     * 上传文件
     * @param file 文件实体
     */
    void upload(File file, MultipartFile multipartFile);
}
