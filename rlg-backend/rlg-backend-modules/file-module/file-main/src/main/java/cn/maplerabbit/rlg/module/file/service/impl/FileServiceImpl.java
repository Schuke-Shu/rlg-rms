package cn.maplerabbit.rlg.module.file.service.impl;

import cn.maplerabbit.rlg.common.entity.LoginPrincipal;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.util.IErrorUtil;
import cn.maplerabbit.rlg.common.util.IFileIOUtil;
import cn.maplerabbit.rlg.module.file.exception.DictionaryCreateException;
import cn.maplerabbit.rlg.module.file.exception.FileCreateException;
import cn.maplerabbit.rlg.module.file.exception.FileError;
import cn.maplerabbit.rlg.module.file.exception.FileException;
import cn.maplerabbit.rlg.module.file.mapper.DictionaryMapper;
import cn.maplerabbit.rlg.module.file.mapper.FileMapper;
import cn.maplerabbit.rlg.module.file.service.IFileService;
import cn.maplerabbit.rlg.pojo.file.dto.MkdirDTO;
import cn.maplerabbit.rlg.pojo.file.dto.SingleUploadDTO;
import cn.maplerabbit.rlg.pojo.file.entity.Dictionary;
import cn.maplerabbit.rlg.pojo.file.entity.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl
        implements IFileService
{
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private IErrorUtil errorUtil;
    @Resource // 文件IO工具有两个实现类，这里使用Resource注解，通过名称注入
    private IFileIOUtil ftpIOUtil;

    private static final String FTP_ROOT = "/";

    public FileServiceImpl()
    {
        log.debug("FileServiceImpl()...");
    }

    @Override
    @Transactional
    public void mkdir(MkdirDTO mkdirDTO, LoginPrincipal principal)
    {
        log.debug("Create directory in dictionary, dto: {}, principal: {}", mkdirDTO, principal);

        Long parentId = mkdirDTO.getParentId();
        // 获取父目录
        Dictionary parent = getParent(parentId);

        // 父目录存在且同级文件中存在重复名称
        if (parent != null && duplicateNameCheck(parentId, mkdirDTO.getFilename()))
            throw new DictionaryCreateException(ServiceCode.ERR_INSERT, "该目录存在同名文件/文件夹");

        log.debug("Parent: {}", parent);

        Dictionary dictionary = newDirectory(principal.getUuid(), mkdirDTO.getFilename(), parent, mkdirDTO.getHidden());

        log.debug("Create dictionary: {}", dictionary);

        // 存储文件索引
        if (dictionaryMapper.save(dictionary) < 1)
            // 存储失败
            throw new DictionaryCreateException(ServiceCode.ERR_INSERT, "文件夹创建失败，请稍后再试");

        log.debug("Success to create directory: {}", mkdirDTO.getFilename());
    }

    @Override
    @Transactional
    public void upload(SingleUploadDTO singleUploadDTO, LoginPrincipal principal)
    {
        log.debug("Create file, dto: {}, principal: {}", singleUploadDTO, principal);

        // 原文件名
        String originFilename = singleUploadDTO.getFile().getOriginalFilename();
        if (!StringUtils.hasText(originFilename))
            throw new FileException(ServiceCode.ERR_BAD_REQUEST, "文件名不能为空");

        try
        {
            // 文件是否有扩展名
            boolean hasExtension = originFilename.contains(".");
            // 获取文件名
            String filename =
                    hasExtension ?
                            originFilename.substring(0, originFilename.lastIndexOf(".")) :
                            originFilename;
            Long parentId = singleUploadDTO.getParentId();
            // 获取父目录
            Dictionary parent = getParent(parentId);

            // 父目录存在且同级文件中存在重复名称
            if (parent != null && duplicateNameCheck(parentId, filename))
                throw new DictionaryCreateException(ServiceCode.ERR_INSERT, "该目录存在同名文件/文件夹");

            log.debug("Parent: {}", parent);

            // 获取文件后缀
            String suffix = hasExtension ? originFilename.substring(filename.length() + 1) : "";
            // 获取文件大小
            Long fileSize = singleUploadDTO.getFile().getSize();
            // 获取文件sha512值
            String sha512 = DigestUtils.sha512Hex(singleUploadDTO.getFile().getInputStream());

            // 尝试从数据库中根据sha512与文件大小查询文件
            File existFile = fileMapper.queryBySha512AndSize(sha512, fileSize);

            // 若数据库中存在该文件，关联该文件uuid，否则生成uuid
            String fileUuid = existFile != null ?
                    existFile.getUuid() :
                    UUID
                            .randomUUID()
                            .toString()
                            .replaceAll("-", "");

            // 创建文件索引实体类
            Dictionary dictionary = newFile(
                    principal.getUuid(),
                    fileUuid,
                    filename,
                    parent,
                    fileSize,
                    singleUploadDTO.getHidden()
            );

            log.debug("Create dictionary: {}", dictionary);

            // 存储文件索引
            if (dictionaryMapper.save(dictionary) < 1)
                // 存储失败
                throw new DictionaryCreateException(ServiceCode.ERR_INSERT, "文件上传失败，请稍后再试");

            // 若数据库中不存在该文件，则存储文件信息至数据库并将文件上传至ftp服务器
            if (existFile == null)
            {
                // 存储文件
                if (
                        fileMapper.save(
                                newFile(dictionary, sha512, singleUploadDTO.getFile(), suffix)
                        )
                        < 1
                )
                    // 存储失败
                    throw new FileCreateException(ServiceCode.ERR_INSERT, "文件上传失败，请稍后再试");

                // 上传至ftp服务器
                ftpIOUtil.upload(FTP_ROOT, fileUuid, singleUploadDTO.getFile());
            }
        }
        catch (IOException e)
        {
            errorUtil.record(this.getClass(), e, FileError.class);
        }
    }

    /**
     * 获取父目录
     *
     * @param parentId 父目录id
     * @return 父目录
     */
    private Dictionary getParent(Long parentId)
    {
        Dictionary parent = null;
        // 如果parentId有效，说明存在父目录
        if (parentId != null && parentId > 0)
        {
            parent = dictionaryMapper.query(parentId);
            // 参数中存在父目录id，但是找不到父目录，报错
            if (parent == null)
                throw new DictionaryCreateException(ServiceCode.ERR_BAD_REQUEST, "父目录不存在");
        }

        return parent;
    }

    /**
     * 名称重复性检查，如果要创建的文件/文件夹名称已存在，返回{@code true}，否则返回{@code false}
     *
     * @param parentId 父目录id
     * @param filename 要创建的文件/文件夹名称
     * @return 名称是否重复
     */
    private boolean duplicateNameCheck(Long parentId, String filename)
    {
        List<Dictionary> list = dictionaryMapper
                .listByParentId(parentId);

        for (Dictionary d : list)
            if (d.getFilename().equals(filename))
                return true;

        return false;
    }

    /**
     * 创建文件夹形式的文件索引实体类对象{@link Dictionary}
     */
    private Dictionary newDirectory(String userUuid, String filename, Dictionary parent, Integer hidden)
    {
        return new Dictionary(
                // id
                null,
                // uuid
                UUID
                        .randomUUID()
                        .toString()
                        .replaceAll("-", ""),
                // user uuid
                userUuid,
                // filename
                filename,
                // deep（如果为null，说明在根目录下，deep为0，否则为父目录deep加一
                parent == null ? NO_PARENT : (parent.getDeep() + 1),
                parent == null ? NO_PARENT : parent.getId(),
                IS_DIRECTORY,
                null,
                NOT_DELETED,
                null,
                hidden,
                null,
                null
        );
    }

    /**
     * 创建文件形式的文件索引实体类对象{@link Dictionary}
     */
    private Dictionary newFile(
            String userUuid,
            String fileUuid,
            String filename,
            Dictionary parent,
            Long fileSize,
            Integer hidden
    )
    {
        return new Dictionary(
                null,
                fileUuid,
                userUuid,
                filename,
                // deep（如果为null，说明在根目录下，deep为0，否则为父目录deep加一
                parent == null ? NO_PARENT : (parent.getDeep() + 1),
                parent == null ? NO_PARENT : parent.getId(),
                NOT_DIRECTORY,
                fileSize,
                NOT_DELETED,
                null,
                hidden,
                null,
                null
        );
    }

    /**
     * 创建文件实体类对象{@link File}
     */
    private File newFile(Dictionary file, String sha512, MultipartFile multipartFile, String suffix)
            throws IOException
    {
        return new File(
                file.getFileUuid(),
                file.getUserUuid(),
                sha512,
                suffix,
                // 获取文件类型
                new Tika().detect(multipartFile.getInputStream()),
                file.getFileSize(),
                LocalDateTime.now(),
                null,
                null
        );
    }
}