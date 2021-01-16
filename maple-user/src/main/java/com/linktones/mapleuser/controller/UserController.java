package com.linktones.mapleuser.controller;
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


import com.linktones.mapleuser.bean.JsonResult;
import com.linktones.mapleuser.entity.SysUser;

import com.linktones.mapleuser.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: 那条蠢鱼
 * @Date: 2020/10/10 01:13
 * @Description: # 描述
 */
@RestController
@RequestMapping(value = "/users",name = "用户管理")
@Api("用户接口")
public class UserController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @GetMapping(value = "/list",name = "获取用户列表(旧)")
    @ApiOperation(value = "获取用户列表(旧)",response = SysUser.class,notes = "获取用户列表,直接返回list对象")
    public List<SysUser> getList(){

        return sysUserMapper.selectList(null);
    }

    @GetMapping(value = "/list2",name = "获取用户列表")
    @ApiOperation(value = "获取用户列表",response = JsonResult.class,notes = "获取用户列表，返回Json")
    public JsonResult getList2(){

        JsonResult jsonResult = new JsonResult();
        jsonResult.setList(sysUserMapper.selectList(null));
        return jsonResult;
    }
}
