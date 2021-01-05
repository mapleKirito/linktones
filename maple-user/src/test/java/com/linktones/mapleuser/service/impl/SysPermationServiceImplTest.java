package com.linktones.mapleuser.service.impl;

import com.linktones.mapleuser.entity.SysPermation;
import com.linktones.mapleuser.service.ISysPermationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/1/1 12:07
 * @Description: # 描述
 */
@SpringBootTest
class SysPermationServiceImplTest {

    @Autowired
    private ISysPermationService sysPermationService;

    @Test
    public void testInitPermation(){
        //测试用例
        new SysPermation();
        List<SysPermation> list=new ArrayList<>();
        list.add(SysPermation.builder().permissionUrl("/demo/f01")
                .permissionModule("demo")
                .permissionDesc("测试清理1111")
                .build());
        sysPermationService.initPermation(list,"demo");
    }
}