package com.linktones.mapleres.utils;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/1/13 00:27
 * @Description: # 资源组件
 */
@Component
public class ResUtils {

    private String[] images={"jpg","png"};
    private String[] videos={"mp4"};

    public String getResType(String resPath){
        String s = StringUtils.substringAfterLast(resPath, ".");
        if(Arrays.asList(images).contains(s)){
            return "image";
        }else if(Arrays.asList(videos).contains(s)){
            return "video";
        }
        return null;
    }
}
