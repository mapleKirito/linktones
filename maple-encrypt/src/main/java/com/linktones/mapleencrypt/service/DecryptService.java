package com.linktones.mapleencrypt.service;
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

import com.linktones.mapleencrypt.model.DecryptBean;
import com.linktones.mapleencrypt.utils.FileUtilInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/5/26 13:11
 * @Description: # 描述
 */
@Service
@Slf4j
public class DecryptService {

    @Autowired
    private FileUtilInterface fui;

    public void decryptDir(DecryptBean decryptBean){

        /*1.遍历源路径*/
        File sourceDirF=new File(decryptBean.getSourceUploadPath());
        if(!(sourceDirF.isDirectory()&&decryptBean.getSourceUploadPath().endsWith("upload"))){
            log.error("[{}]不是upload目录",decryptBean.getSourceUploadPath());
            return;
        }

        /*2.处理*/
        fui.init(decryptBean.getOutputPath());
        viewDir(sourceDirF,fui);

    }


    /**
     * 遍历文件夹
     * @param dirF
     */
    private void viewDir(File dirF, FileUtilInterface fui){
        LinkedList<File> fileList=new LinkedList<>();
        if(null!=dirF.listFiles()){
            log.info("[Root]:{}",dirF.getAbsolutePath());
            fileList.addAll(Arrays.asList(dirF.listFiles()));
            while(!fileList.isEmpty()){
                File tmpFile=fileList.removeFirst();

                if(!tmpFile.isDirectory()){
                    /*文件*/
                    log.info("[File]:{}",tmpFile.getAbsolutePath());
                    fui.doWithPath(tmpFile.getAbsolutePath());
                    continue;
                }

                File[] files = tmpFile.listFiles();
                if(null==files){
                    /*空目录*/
                    continue;
                }

                for (File file : files) {
                    if(file.isDirectory()){
                        /*文件夹*/
                        log.info("[Dir]:{}",file.getAbsolutePath());
                        fileList.add(file);
                    }else{
                        /*文件*/
                        log.info("[File]:{}",file.getAbsolutePath());
                        fui.doWithPath(file.getAbsolutePath());
                    }

                }

            }
        }
    }
}
