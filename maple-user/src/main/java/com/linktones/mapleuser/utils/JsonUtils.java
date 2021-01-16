package com.linktones.mapleuser.utils;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/1/16 22:51
 * @Description: # fastJson常用工具
 */
@Component
@Slf4j
public class JsonUtils {

    public boolean checkParamNull(JSONObject jsonObject,String... checkKeys){
        boolean result=true;
        for (String checkKey : checkKeys) {
            if(StringUtils.isBlank(jsonObject.getString(checkKey))){
                log.info("字段[{}]不存在或者值为空",checkKey);
                result= false;
                break;
            }
        }

        return result;
    }
}
