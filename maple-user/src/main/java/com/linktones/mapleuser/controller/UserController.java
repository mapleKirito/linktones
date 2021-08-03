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
import com.linktones.mapleuser.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private ISysUserService userService;

    @GetMapping(value = "/list_old",name = "获取用户列表(旧)")
    @ApiOperation(value = "获取用户列表(旧)",response = SysUser.class,notes = "获取用户列表,直接返回list对象")
    public List<SysUser> getList(){

        return userService.list();
    }

    @GetMapping(value = "list",name = "获取用户列表")
    @ApiOperation(value = "获取用户列表",response = JsonResult.class,notes = "获取用户列表，返回Json")
    public JsonResult getList2(){

        JsonResult jsonResult = new JsonResult();
        jsonResult.setList(userService.list());
        return jsonResult;
    }

    @PostMapping(value = "add",name = "添加用户")
    @ApiOperation(value = "添加用户",response = JsonResult.class,notes = "添加新用户")
    public JsonResult add(SysUser user){
        JsonResult jsonResult = new JsonResult();
        boolean tag = userService.save(user);
        if(tag){
            jsonResult.setSuccess("添加用户成功");
        }else{
            jsonResult.setFail("添加用户失败");
        }
        return jsonResult;
    }

    @PostMapping(value = "/del/{id}",name = "删除用户")
    @ApiOperation(value = "删除用户",response = JsonResult.class,notes = "删除用户")
    public JsonResult del(@PathVariable String id){
        JsonResult jsonResult = new JsonResult();
        boolean tag = userService.removeById(id);
        if(tag){
            jsonResult.setSuccess("删除用户成功");
        }else{
            jsonResult.setFail("删除用户失败");
        }
        return jsonResult;
    }

    @PostMapping(value = "/modify/{id}",name = "变更用户")
    @ApiOperation(value = "变更用户",response = JsonResult.class,notes = "变更用户")
    public JsonResult modify(@PathVariable String id,SysUser user){
        JsonResult jsonResult = new JsonResult();
        boolean tag = userService.updateById(user);
        if(tag){
            jsonResult.setSuccess("变更用户成功");
        }else{
            jsonResult.setFail("变更用户失败");
        }
        return jsonResult;
    }

    @PostMapping(value = "/role/{id}",name = "更新用户角色信息")
    @ApiOperation(value = "更新用户角色信息",response = JsonResult.class,notes = "更新用户角色信息")
    public JsonResult changeRoleBind(@PathVariable String id,String[] roleIds){
        JsonResult jsonResult = new JsonResult();
        return jsonResult;
    }

    @GetMapping(value = "/{id}",name = "查询用户信息")
    @ApiOperation(value = "查询用户信息",response = JsonResult.class,notes = "查询用户信息")
    public JsonResult modify(@PathVariable String id){
        JsonResult jsonResult = new JsonResult();
        SysUser userInfo = userService.getById(id);
        if(null!=userInfo){
            jsonResult.setData(userInfo);
        }else{
            jsonResult.setFail("找不到该用户");
        }
        return jsonResult;
    }
}
