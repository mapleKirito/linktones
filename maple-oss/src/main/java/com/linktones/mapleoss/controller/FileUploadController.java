package com.linktones.mapleoss.controller;
/***
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                    O\ = /O
 *                ____/`---'\____
 *              .   ' \\| |// `.
 *               / \\||| : |||// \
 *             / _||||| -:- |||||- \
 *               | | \\\ - /// | |
 *             | \_| ''\---/'' | |
 *              \ .-\__ `-` ___/-. /
 *           ___`. .' /--.--\ `. . __
 *        ."" '< `.___\_<|>_/___.' >'"".
 *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *         \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                    `=---='
 *
 * .............................................
 *          佛祖保佑             永无BUG
 */

import com.linktones.mapleoss.model.vo.FileInfoVo;
import com.linktones.mapleoss.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/1/6 00:09
 * @Description: # 文件上传
 */
@RestController
@RequestMapping("file/upload")
@Slf4j
@Api("文件上传")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    @ApiOperation(value = "文件上传接口")
    public FileInfoVo uploadFile(@RequestParam("file")MultipartFile multipartFile){
        return fileUploadService.upload(multipartFile);

    }
}
