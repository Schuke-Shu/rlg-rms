package cn.maplerabbit.rlg.module.file.controller;

import cn.maplerabbit.rlg.common.entity.LoginPrincipal;
import cn.maplerabbit.rlg.common.entity.result.JsonResult;
import cn.maplerabbit.rlg.common.entity.result.SuccessResult;
import cn.maplerabbit.rlg.module.file.service.IFileService;
import cn.maplerabbit.rlg.pojo.file.dto.MkdirDTO;
import cn.maplerabbit.rlg.pojo.file.dto.SingleUploadDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 文件模块控制器
 */
@Slf4j
@RestController
@RequestMapping(value = "/file")
@Api(tags = "02. 文件模块")
public class FileController
{
    @Autowired
    private IFileService fileService;

    public FileController() {log.debug("FileController()...");}

    private static final String CREATE_FILE_NOTE = "如果要在根目录下创建文件或文件夹，将父目录设置为无效值即可（null或小于1的数，一般不填即可）";

    @ApiOperation(value = "创建文件夹", notes = CREATE_FILE_NOTE)
    @PostMapping("/mkdir")
    public JsonResult<?> mkdir(
            @Valid MkdirDTO mkdirDTO,
            @ApiIgnore @AuthenticationPrincipal LoginPrincipal principal
    )
    {
        fileService.mkdir(mkdirDTO, principal);
        return SuccessResult.ok();
    }

    @ApiOperation("单文件上传")
    @PostMapping("/upload-single")
    public JsonResult<?> upload(
            @Valid SingleUploadDTO singleUploadDTO,
            @ApiIgnore @AuthenticationPrincipal LoginPrincipal principal
    )
    {
        fileService.upload(singleUploadDTO, principal);
        return SuccessResult.ok();
    }
}