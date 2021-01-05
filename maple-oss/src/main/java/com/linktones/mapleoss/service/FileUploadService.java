package com.linktones.mapleoss.service;
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

import com.aliyun.oss.OSS;
import com.linktones.mapleoss.config.OSSConfig;
import com.linktones.mapleoss.model.vo.FileInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/1/6 00:10
 * @Description: # 文件上传
 */
@Service
@Slf4j
public class FileUploadService {

    @Autowired
    private OSS ossClient;
    @Autowired
    private OSSConfig ossConfig;

    public FileInfoVo upload(MultipartFile multipartFile) {

        if (ossClient.doesBucketExist(ossConfig.getBucketName())) {
            log.info("验证BucketName[{}]通过",ossConfig.getBucketName());
        } else {
            log.error("BucketName[{}]不存在",ossConfig.getBucketName());
        }

        String filePath=mkUploadPath(multipartFile.getOriginalFilename());
        //上传
        try {
            ossClient.putObject(ossConfig.getBucketName(),filePath,multipartFile.getInputStream());
        } catch (IOException e) {
            log.error("OSS上传异常:{}",e.getMessage());
        }
        return FileInfoVo.builder()
                .fileName(multipartFile.getOriginalFilename())
                .filePath(ossConfig.getViewPath()+filePath)
                .build();

    }


    private String mkUploadPath(String fileName){
        return ossConfig.getUserDir()+"u_"+System.currentTimeMillis()
                + RandomUtils.nextInt(new Random(),100)
                +"."+ StringUtils.substringAfterLast(fileName,".");
    }

}
