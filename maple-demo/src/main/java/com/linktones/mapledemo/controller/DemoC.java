package com.linktones.mapledemo.controller;
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

import com.linktones.mapledemo.bean.R;
import com.linktones.mapledemo.bean.RUtils;
import com.linktones.mapledemo.feign.UserPermissionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/6/9 01:22
 * @Description: # 描述
 */
@RestController
@RequestMapping("/demo")
public class DemoC {
    @Autowired
    private UserPermissionInterface userPermission;

    @GetMapping("/f01")
    public String demo(){
        return userPermission.getList();
    }

    @Value("${maple.name:NaN}")
    private String mapleName;

    @RequestMapping("f02")
    public R demoConfig(){
        return RUtils.success(mapleName);
    }

}
