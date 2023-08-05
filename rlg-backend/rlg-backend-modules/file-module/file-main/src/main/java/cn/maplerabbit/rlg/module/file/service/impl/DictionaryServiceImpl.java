package cn.maplerabbit.rlg.module.file.service.impl;

import cn.maplerabbit.rlg.common.entity.LoginPrincipal;
import cn.maplerabbit.rlg.common.enumpak.ServiceCode;
import cn.maplerabbit.rlg.common.util.IErrorUtil;
import cn.maplerabbit.rlg.module.file.exception.DictionaryCreateException;
import cn.maplerabbit.rlg.module.file.exception.FileError;
import cn.maplerabbit.rlg.module.file.mapper.DictionaryMapper;
import cn.maplerabbit.rlg.module.file.service.IDictionaryService;
import cn.maplerabbit.rlg.module.file.service.IFileService;
import cn.maplerabbit.rlg.pojo.file.dto.MkdirDTO;
import cn.maplerabbit.rlg.pojo.file.entity.Dictionary;
import cn.maplerabbit.rlg.pojo.file.entity.File;
import cn.maplerabbit.rlg.pojo.file.vo.ListFileVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件索引服务接口实现类
 */
@Slf4j
@Service
public class DictionaryServiceImpl
        implements IDictionaryService
{
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IErrorUtil errorUtil;

    private static final UserLock LOCK = new UserLock();

    public DictionaryServiceImpl() {log.debug("DictionaryServiceImpl()...");}

    @Override
    @Transactional
    public void mkdir(MkdirDTO mkdirDTO, LoginPrincipal principal)
    {
        log.debug("Create directory: {}, user: {}", mkdirDTO.getName(), principal.getUsername());

        String userUuid = principal.getUuid();

        synchronized (LOCK.lock(userUuid))
        {
            Dictionary parent = getParent(mkdirDTO.getParentId(), userUuid);

            if (
                    dictionaryMapper.queryByParentIdAndFilename(
                            userUuid,
                            parent == null ? NO_PARENT : parent.getId(),
                            mkdirDTO.getName()
                    )
                            != null
            )
                throw new DictionaryCreateException(ServiceCode.ERR_CONFLICT, "存在同名文件/文件夹");

            // 创建文件夹索引对象，并存入数据库
            newDirectory(
                    userUuid,
                    mkdirDTO.getName(),
                    parent
            );

            LOCK.unlock(userUuid);
        }
    }

    /**
     * 创建文件夹形式的文件索引实体类对象{@link Dictionary}，并存储至数据库
     *
     * @param userUuid 用户uuid
     * @param filename 文件名
     * @param parent   父目录
     */
    private void newDirectory(String userUuid, String filename, Dictionary parent)
    {
        Dictionary directory = new Dictionary()
                .setUserUuid(userUuid)
                .setFilename(filename)
                .setPath(path(parent, filename))
                // 如果为null，说明在根目录下，deep为0，否则为父目录deep加一
                .setDeep(deep(parent))
                .setParentId(parentId(parent))
                .setDirectory(IS_DIRECTORY)
                .setDeleted(NOT_DELETED)
                .setDeleteTime(null)
                // 是否隐藏继承自父目录，如果没有父目录，默认不隐藏
                .setHidden(hidden(parent));

        if (dictionaryMapper.save(directory) < 1)
            throw new DictionaryCreateException(ServiceCode.ERR_INSERT, "文件夹创建失败，请稍后再试");

        log.debug("New directory: {}", directory);
    }

    @Override
    public void upload(Long parentId, MultipartFile file, LoginPrincipal principal)
    {
        log.debug("Upload file: {}, user: {}", file.getOriginalFilename(), principal.getUsername());

        String userUuid = principal.getUuid();

        Dictionary parent = getParent(parentId, userUuid);

        // 解析文件名
        FilenameParser.FileDetail detail;

        synchronized (LOCK.lock(userUuid))
        {
            detail = new FilenameParser(parent, file.getOriginalFilename(), userUuid)
                    .parse();

            LOCK.unlock(userUuid);
        }

        try
        {
            String sha512 = DigestUtils.sha512Hex(file.getInputStream());
            File existFile = fileService.ftpExist(sha512, file.getSize());

            // 如果文件存在，使用文件uuid，否则生成uuid
            String fileUuid = existFile != null ? existFile.getUuid() : uuid();

            if (dictionaryMapper.queryByParentIdAndFilename(userUuid, detail.parent.getId(), detail.filename) != null)
                detail.filename = addSuffix(detail.filename);

            // 存储文件索引至数据库
            Dictionary dFile = newFile(fileUuid, userUuid, file, detail);

            // 如果文件实体不存在，须要上传
            if (existFile == null)
                fileService.upload(
                        newFile(dFile, sha512, file),
                        file
                );
        }
        catch (IOException e)
        {
            errorUtil.record(this.getClass(), e, FileError.class);
        }
    }

    /**
     * 获取父目录{@link Dictionary}
     *
     * @param parentId 父目录id
     * @return 父目录
     */
    private Dictionary getParent(Long parentId, String userUuid)
    {
        Dictionary parent = null;
        // 如果parentId有效，说明存在父目录
        if (parentId != null && parentId > 0)
        {
            parent = dictionaryMapper.query(parentId);
            // 参数中存在父目录id，但是找不到父目录，报错
            if (parent == null)
                throw new DictionaryCreateException(ServiceCode.ERR_BAD_REQUEST, "该目录不存在");
        }

        // 父目录存在且该目录所属用户与当前操作用户不同
        if (parent != null && !parent.getUserUuid().equals(userUuid))
            throw new DictionaryCreateException(ServiceCode.ERR_FORBIDDEN, "非法操作");

        log.debug("Parent: {}", parent);

        return parent;
    }

    /**
     * 创建文件形式的文件索引实体类对象{@link Dictionary}，并存储至数据库
     *
     * @return 文件索引
     */
    private Dictionary newFile(String fileUuid, String userUuid, MultipartFile multipartFile, FilenameParser.FileDetail detail)
    {
        Dictionary parent = detail.parent;
        String filename = detail.filename;

        Dictionary file = new Dictionary()
                .setFileUuid(fileUuid)
                .setUserUuid(userUuid)
                // 父目录、文件名与扩展名必须解析后的从文件信息中获取
                .setFilename(filename)
                .setPath(path(parent, filename))
                .setDeep(deep(parent))
                .setParentId(parentId(parent))
                .setDirectory(NOT_DIRECTORY)
                .setFileSize(multipartFile.getSize())
                .setDeleted(NOT_DELETED)
                .setDeleteTime(null)
                // 是否隐藏继承自父目录，如果没有父目录，默认不隐藏
                .setHidden(hidden(parent));

        if (dictionaryMapper.save(file) < 1)
            throw new DictionaryCreateException(ServiceCode.ERR_INSERT, "文件创建失败，请稍后再试");

        log.debug("New file: {}", file);

        return file;
    }


    /**
     * 创建文件实体类对象{@link File}
     */
    private File newFile(Dictionary file, String sha512, MultipartFile multipartFile)
            throws IOException
    {
        return new File()
                .setUuid(file.getFileUuid())
                .setUserUuid(file.getUserUuid())
                .setSha512(sha512)
                .setType(new Tika().detect(multipartFile.getInputStream()))
                .setSize(multipartFile.getSize())
                .setUploadTime(LocalDateTime.now());
    }

    /**
     * 获取随机uuid
     *
     * @return uuid
     */
    private String uuid()
    {
        return UUID
                .randomUUID()
                .toString()
                .replaceAll("-", "");
    }

    private String addSuffix(String filename)
    {
        return new StringBuilder(filename)
                .append("_")
                .append(LocalDate.now().toString().replaceAll("-", ""))
                .append("_")
                .append(Long.toHexString(System.currentTimeMillis() % ONE_DAY_MILLIS))
                .toString();
    }

    private String path(Dictionary parent, String filename) {return parent == null ? (ROOT + filename) : (parent.getPath() + "/" + filename);}

    private Integer deep(Dictionary parent) {return parent == null ? NO_PARENT : (parent.getDeep() + 1);}

    private Long parentId(Dictionary parent) {return parent == null ? NO_PARENT : parent.getId();}

    private Integer hidden(Dictionary parent) {return parent == null ? NOT_HIDDEN : parent.getHidden();}

    @AllArgsConstructor
    private class FilenameParser
    {
        private static final String separator = "/";
        /**
         * 当前目录
         */
        private Dictionary currentDirectory;
        /**
         * 文件名
         */
        private String filename;
        /**
         * 用户uuid
         */
        private String userUuid;

        @Transactional
        private FileDetail parse()
        {
            log.debug("Parse filename...");
            log.trace("current directory: {}, filename: {}, user uuid: {}", currentDirectory, filename, userUuid);

            // 如果有分隔符，说明为文件夹上传
            if (filename.contains(separator))
            {
                // 获取文件路径，并循环切换直至目标文件的父目录，沿途创建目录
                String[] paths = filename.split("/");
                for (int i = 0; i < paths.length - 1; i++)
                    if (StringUtils.hasText(paths[i]))
                        changeCurrentDirectory(paths[i]);

                filename = paths[paths.length - 1];
            }

            return new FileDetail(currentDirectory, filename);
        }

        /**
         * 切换当前目录，若目标目录不存在则创建
         *
         * @param childDir 子目录名称
         */
        private void changeCurrentDirectory(String childDir)
        {
            // 获取当前目录id，如果为null，说明当前目录为根目录
            Long id = currentDirectory == null ? NO_PARENT : currentDirectory.getId();

            // 根据目录名称获取子目录
            Dictionary directory = dictionaryMapper.queryByParentIdAndFilename(userUuid, id, childDir);

            // 如果目录不存在，创建目录
            if (directory == null)
            {
                directory = new Dictionary()
                        .setUserUuid(userUuid)
                        .setFilename(childDir)
                        .setPath(path(currentDirectory, childDir))
                        .setDeep(deep(currentDirectory))
                        .setParentId(id)
                        .setDirectory(IS_DIRECTORY)
                        .setDeleted(NOT_DELETED)
                        .setDeleteTime(null)
                        .setHidden(NOT_HIDDEN);

                if (dictionaryMapper.save(directory) < 1)
                    throw new DictionaryCreateException(ServiceCode.ERR_INSERT, "文件上传失败，请稍后再试");
            }

            // 切换至子目录
            currentDirectory = directory;
            log.debug("Change current directory to: {}", directory);
        }

        @AllArgsConstructor
        private class FileDetail
        {
            /**
             * 父目录
             */
            private Dictionary parent;
            /**
             * 文件名
             */
            private String filename;
        }
    }

    @Override
    public List<ListFileVO> list(String path, LoginPrincipal principal)
    {
        return dictionaryMapper.listByPath(principal.getUuid(), path);
    }
}

/**
 * 用户锁
 */
@Getter
class UserLock
{
    /**
     * 用户锁池
     */
    private static final Map<String, Object> LOCK_POOL = new HashMap<>();
    /**
     * 等待池，用于判断是否有线程在等待锁
     */
    private static final Map<String, AtomicInteger> WAITING_POOL = new HashMap<>();

    /**
     * 生成锁
     * @param uuid 用户uuid
     * @return 锁
     */
    public Object lock(String uuid)
    {
        Object lock;
        synchronized (LOCK_POOL)
        {
            WAITING_POOL.computeIfAbsent(uuid, v -> new AtomicInteger()).incrementAndGet();
            lock = LOCK_POOL.computeIfAbsent(uuid, v -> new Object());
        }
        return lock;
    }

    /**
     * 解锁
     * @param uuid 用户uuid
     */
    public void unlock(String uuid)
    {
        synchronized (LOCK_POOL)
        {
            if (WAITING_POOL.get(uuid).decrementAndGet() == 0)
            {
                LOCK_POOL.remove(uuid);
                WAITING_POOL.remove(uuid);
            }
        }
    }
}