package com.linktones.mapleencrypt.service.thread;
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

import com.alibaba.fastjson.JSONObject;
import com.linktones.mapleencrypt.model.DecryptBean;
import com.linktones.mapleencrypt.utils.Decrypt2;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/5/29 19:57
 * @Description: # 描述
 */
@Slf4j
public class DecryptThread extends Thread {
    private Session session;
    private int count;
    private DecryptBean decryptBean;
    private LinkedList<String> pathList;

    private Decrypt2 decrypt=new Decrypt2();

    public DecryptThread(Session session, DecryptBean decryptBean){
        this.session=session;
        this.count=0;
        this.decryptBean=decryptBean;
        this.pathList=new LinkedList<String>();
    }

    @Override
    public void run() {
        try {
            /*1.遍历源路径*/
            File sourceDirF=new File(decryptBean.getSourceUploadPath());
            if(!(sourceDirF.isDirectory()&&decryptBean.getSourceUploadPath().endsWith("upload"))){
                log.error("[{}]不是upload目录",decryptBean.getSourceUploadPath());
                return;
            }

            /*2.初始化输出目录*/
            decrypt.init(decryptBean.getOutputPath());
            /*3.生成文件清单*//*
            viewDir(sourceDirF);
            count=pathList.size();
            *//*4.开始处理，输出结果*//*
            while (pathList.size()>0){
                String tmp=pathList.removeFirst();
                decrypt.doWithPath(tmp);
                *//*5.输出处理结果*//*
                JSONObject json=new JSONObject();
                json.put("msg",tmp+"处理完成");
                //剩余量
                json.put("count",pathList.size());
                //总量
                json.put("max",count);

                session.getBasicRemote().sendText(json.toJSONString());
            }*/
            synchronized (this.session){

                /*3.执行*/
                viewDir2(sourceDirF);
                /*4.发送结束消息*/
                JSONObject json=new JSONObject();
                json.put("msg","finish");
                session.getBasicRemote().sendText(json.toJSONString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历文件夹
     * @param dirF
     */
    private void viewDir(File dirF){
        LinkedList<File> fileList=new LinkedList<>();
        if(null!=dirF.listFiles()){
            log.info("[Root]:{}",dirF.getAbsolutePath());
            fileList.addAll(Arrays.asList(dirF.listFiles()));
            while(!fileList.isEmpty()){
                File tmpFile=fileList.removeFirst();

                if(!tmpFile.isDirectory()){
                    /*文件*/
                    log.info("[File]:{}",tmpFile.getAbsolutePath());
                    //fui.doWithPath(tmpFile.getAbsolutePath());
                    pathList.add(tmpFile.getAbsolutePath());
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
                        //fui.doWithPath(file.getAbsolutePath());
                        pathList.add(tmpFile.getAbsolutePath());
                    }

                }

            }
        }
    }

    /**
     * 遍历文件夹
     * @param dirF
     */
    private void viewDir2(File dirF){
        try{

            LinkedList<File> fileList=new LinkedList<>();
            if(null!=dirF.listFiles()){
                log.info("[Root]:{}",dirF.getAbsolutePath());
                fileList.addAll(Arrays.asList(dirF.listFiles()));
                while(!fileList.isEmpty()){
                    File tmpFile=fileList.removeFirst();

                    if(!tmpFile.isDirectory()){
                        /*文件*/
                        log.info("[File]:{}",tmpFile.getAbsolutePath());
                        //fui.doWithPath(tmpFile.getAbsolutePath());
                        //pathList.add(tmpFile.getAbsolutePath());
                        String msg=decrypt.doWithPath(tmpFile.getAbsolutePath());
                        /*5.输出处理结果*/
                        JSONObject json=new JSONObject();
                        json.put("msg",msg);
                        session.getBasicRemote().sendText(json.toJSONString());
                        continue;
                    }

                    File[] files = tmpFile.listFiles();
                    if(null==files){
                        /*空目录*/
                        continue;
                    }

                    fileList.addAll(Arrays.asList(files));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
