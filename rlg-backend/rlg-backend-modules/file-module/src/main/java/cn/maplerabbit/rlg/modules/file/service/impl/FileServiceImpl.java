package cn.maplerabbit.rlg.modules.file.service.impl;

import cn.maplerabbit.rlg.modules.file.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileServiceImpl implements IFileService
{
    public FileServiceImpl()
    {
        log.trace("FileServiceImpl()...");
    }
}