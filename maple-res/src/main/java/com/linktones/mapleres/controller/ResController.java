package com.linktones.mapleres.controller;
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
import com.aliyun.oss.model.OSSObject;
import com.linktones.mapleres.config.OSSConfig;
import com.linktones.mapleres.utils.ResUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/1/13 00:23
 * @Description: # 资源接口
 */
@Controller
@Slf4j
@RequestMapping("/res")
public class ResController {

    @Autowired
    private ResUtils resUtils;

    @Autowired
    private OSS ossClient;
    @Autowired
    private OSSConfig ossConfig;

    /**
     * 公共资源访问接口
     * @param resourcePath
     * @param response
     * @throws Exception
     */
    @GetMapping
    @ResponseBody
    public void res(@RequestParam(value="info", required=false) String resourcePath, HttpServletResponse response) throws Exception {

        InputStream iStream=null;
        try {
            //判断类型
            if("image".equals(resUtils.getResType(resourcePath))){
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            }else if("video".equals(resUtils.getResType(resourcePath))){
                response.setContentType("video/mp4");
            }else{
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            }

            OSSObject ossObject = ossClient.getObject(ossConfig.getBucketName(), resourcePath);
            iStream = ossObject.getObjectContent();
            IOUtils.copy(iStream, response.getOutputStream());
            response.flushBuffer();
        } catch (java.nio.file.NoSuchFileException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }finally {
            if(null!=iStream){
                iStream.close();
            }
        }
    }

    /**
     * 分段资源
     * @param resourcePath
     * @param response
     * @throws Exception
     */
    @GetMapping("/large")
    @ResponseBody
    public void largeRes(@RequestParam(value="info", required=false) String resourcePath, HttpServletResponse response) throws Exception {

        InputStream iStream=null;
        try {
            //判断类型
            if("image".equals(resUtils.getResType(resourcePath))){
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            }else if("video".equals(resUtils.getResType(resourcePath))){
                response.setContentType("video/mp4");
            }else{
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            }

            OSSObject ossObject = ossClient.getObject(ossConfig.getBucketName(), resourcePath);
            iStream = ossObject.getObjectContent();
            IOUtils.copy(iStream, response.getOutputStream());
            response.flushBuffer();
        } catch (java.nio.file.NoSuchFileException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }finally {
            if(null!=iStream){
                iStream.close();
            }
        }
    }

    /**
     * 分段验证
     * @param response
     * @throws Exception
     */
    @GetMapping("/demo")
    @ResponseBody
    public void demo( HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.reset();
        File file = new File("F:\\研发\\aa.mp4");
        long fileLength = file.length();
        // 随机读文件
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

        //获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");
        long range=0;
        if (StringUtils.isNotBlank(rangeString)) {
            range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
        }
        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        //设置内容类型
        response.setHeader("Content-Type", "video/mp4");
        //返回码需要为206，代表只处理了部分请求，响应了部分数据
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        // 移动访问指针到指定位置
        randomAccessFile.seek(range);
        // 每次请求只返回1MB的视频流
        byte[] bytes = new byte[1024 * 1024];
        int len = randomAccessFile.read(bytes);
        //设置此次相应返回的数据长度
        response.setContentLength(len);
        //设置此次相应返回的数据范围
        response.setHeader("Content-Range", "bytes "+range+"-"+(fileLength-1)+"/"+fileLength);
        // 将这1MB的视频流响应给客户端
        outputStream.write(bytes, 0, len);
        outputStream.close();
        randomAccessFile.close();

        log.info("返回数据区间:【{}-{}】",range,(range+len));
    }
}
