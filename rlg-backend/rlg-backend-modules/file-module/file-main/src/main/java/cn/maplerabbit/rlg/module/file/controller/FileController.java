package cn.maplerabbit.rlg.module.file.controller;

import cn.maplerabbit.rlg.common.entity.LoginPrincipal;
import cn.maplerabbit.rlg.common.entity.result.JsonResult;
import cn.maplerabbit.rlg.common.entity.result.SuccessResult;
import cn.maplerabbit.rlg.module.file.service.IDictionaryService;
import cn.maplerabbit.rlg.pojo.file.dto.MkdirDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    private IDictionaryService dictionaryService;

    public FileController() {log.debug("FileController()...");}

    private static final String CREATE_FILE_NOTE = "如果要在根目录下创建文件或文件夹，将父目录设置为无效值即可（null或小于1的数，一般不填即可）";

    @ApiOperation(value = "创建文件夹", notes = CREATE_FILE_NOTE)
    @GetMapping("/mkdir")
    public JsonResult<?> mkdir(
            @Valid MkdirDTO mkdirDTO,
            @ApiIgnore @AuthenticationPrincipal LoginPrincipal principal
    )
    {
        dictionaryService.mkdir(mkdirDTO, principal);
        return SuccessResult.ok();
    }

    private static final String UPLOAD_NOTE = "只支持单文件上传，多文件上传与文件夹上传在前端实现";

    @ApiOperation(value = "文件上传", notes = UPLOAD_NOTE)
    @PostMapping("/upload/{parentId:\\d+}")
    public JsonResult<?> upload(
            @PathVariable Long parentId,
            @NotNull MultipartFile file,
            @ApiIgnore @AuthenticationPrincipal LoginPrincipal principal
    )
    {
        dictionaryService.upload(parentId, file, principal);
        return SuccessResult.ok();
    }

    @ApiOperation("文件列表")
    @PostMapping("/list")
    @ApiImplicitParam(value = "父目录路径", name = "path", dataTypeClass = Long.class)
    public JsonResult<?> list(String path, @ApiIgnore @AuthenticationPrincipal LoginPrincipal principal)
    {
        return SuccessResult.ok(
                dictionaryService.list(path, principal)
        );
    }

    @ApiOperation("文件复制")
    @GetMapping("/copy/{fileId:\\d+}/to/{targetParentId:\\d+}")
    public JsonResult<?> copy(
            @PathVariable Long fileId,
            @PathVariable Long targetParentId,
            @ApiIgnore @AuthenticationPrincipal LoginPrincipal principal
    )
    {
        return SuccessResult.ok();
    }

    @ApiOperation("文件移位")
    @GetMapping("/move/{fileId:\\d+}/to/{targetParentId:\\d+}")
    public JsonResult<?> move(
            @PathVariable Long fileId,
            @PathVariable Long targetParentId,
            @ApiIgnore @AuthenticationPrincipal LoginPrincipal principal
    )
    {
        return SuccessResult.ok();
    }

    @ApiOperation("文件删除")
    @GetMapping("/delete/{id:\\d+}")
    public JsonResult<?> delete(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal LoginPrincipal principal)
    {
        return SuccessResult.ok();
    }
}