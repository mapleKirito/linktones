package com.linktones.mapleencrypt.controller;
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
import com.linktones.mapleencrypt.model.GlobleBean;
import com.linktones.mapleencrypt.service.DecryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2021/5/26 11:50
 * @Description: # 描述
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private DecryptService decryptService;



    @ResponseBody
    @PostMapping("decrypt")
    public String decryptFiles(DecryptBean decryptBean){
        decryptService.decryptDir(decryptBean);
        return "ok";
    }

    @PostMapping("toDecrypt")
    public String toDecrypt(@ModelAttribute DecryptBean decryptBean){
        GlobleBean.decryptBean=decryptBean;
        return "decryptInfo";
    }

    @GetMapping("hello")
    public String hello(@RequestParam(defaultValue = "default") String name, Model model){
        model.addAttribute("name",name);
        return "hello";
    }

    @GetMapping("/start")
    public String index(Model model){
        model.addAttribute("decryptBean",new DecryptBean());
        return "main";
    }
}
