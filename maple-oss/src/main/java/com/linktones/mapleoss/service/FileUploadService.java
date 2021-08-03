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
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public String uploadByXls(){
        //生成日志文件名，构建线程，返回日志名称
        String logName=createLogName();
        String property = System.getProperty("user.dir");
        String logFilePath=property+"/logs/"+logName;
        File logFile=new File(logFilePath);
        if(!logFile.exists()){
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                log.error("创建文件[{}]失败",logFilePath);
                e.printStackTrace();
            }
        }
        log.info("初始化批量上传日志文件成功:{}",logFilePath);
        //读xls
        //遍历，执行fileUpload
        //根据返回结果生成日志，打印到文件

        return logName;
    }

    private String createLogName(){
        //规范,根据日期时间+后缀生成
        LocalDateTime nowTime=LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
        String format = nowTime.format(dateTimeFormatter);
        format+=".log";
        return format;
    }

    private boolean fileUpload(File file,String uploadPath) {

        FileInputStream fis=null;

        if (ossClient.doesBucketExist(ossConfig.getBucketName())) {
            log.info("验证BucketName[{}]通过",ossConfig.getBucketName());
        } else {
            log.error("BucketName[{}]不存在",ossConfig.getBucketName());
        }

        //上传
        try {
            fis=new FileInputStream(file);
            ossClient.putObject(ossConfig.getBucketName(),uploadPath,fis);
            return true;
        } catch (IOException e) {
            log.error("OSS上传异常:{}",e.getMessage());
            return false;
        }finally {
            IOUtils.closeQuietly(fis);
        }

    }


    private String mkUploadPath(String fileName){
        return ossConfig.getUserDir()+"u_"+System.currentTimeMillis()
                + RandomUtils.nextInt(new Random(),100)
                +"."+ StringUtils.substringAfterLast(fileName,".");
    }

}
