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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/1/13 00:27
 * @Description: # 资源组件
 */
@Component
public class ResUtils {

    @Value("${maple.res.images}")
    private String[] images;

    @Value("${maple.res.videos}")
    private String[] videos;

    public String getResType(String resPath){
        String s = StringUtils.substringAfterLast(resPath, ".");
        if(Arrays.asList(images).stream().anyMatch(x -> x.equalsIgnoreCase(s))){
            return "image";
        }else if(Arrays.asList(videos).stream().anyMatch(x -> x.equalsIgnoreCase(s))){
            return "video";
        }
        return null;
    }
}
