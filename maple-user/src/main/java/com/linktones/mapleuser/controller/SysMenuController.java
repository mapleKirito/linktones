package com.linktones.mapleuser.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linktones.mapleuser.bean.JsonResult;
import com.linktones.mapleuser.entity.SysMenu;
import com.linktones.mapleuser.service.ISysMenuService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author maple
 * @since 2021-03-01
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单列表",notes = "菜单列表，根据roleId与parentIndex获取当前级别的菜单")
    @PostMapping("list")
    public JsonResult getlist(SysMenu menu ){
        QueryWrapper<SysMenu> menuQueryWrapper=new QueryWrapper<>();
        menuQueryWrapper.lambda().eq(SysMenu::getMRoleId,menu.getMRoleId()).eq(SysMenu::getMParentIndex,menu.getMParentIndex());
        List<SysMenu> list = sysMenuService.list(menuQueryWrapper);
        return JsonResult.successList(list,"获取菜单成功");
    }

    @PostMapping()
    @ApiOperation(value = "添加菜单项")
    public JsonResult add(@RequestBody SysMenu menu){
        JsonResult result=new JsonResult();
        if(!sysMenuService.save(menu)){
            result.setFail("添加失败");
        }

        return result;
    }

    @DeleteMapping("/{mId}")
    @ApiOperation(value = "删除菜单项")
    public JsonResult delete(@PathVariable String mId){
        JsonResult result=new JsonResult();
        if(!sysMenuService.removeById(mId)){
            result.setFail("删除失败");
        }

        return result;
    }

    @PutMapping("/{mId}")
    @ApiOperation(value = "更新菜单项")
    public JsonResult modify(@PathVariable String mId,@RequestBody SysMenu menu){
        JsonResult result=new JsonResult();
        menu.setMId(mId);
        if(!sysMenuService.updateById(menu)){
            result.setFail("更新失败");
        }

        return result;
    }

    @GetMapping("/{mId}")
    @ApiOperation(value = "查看菜单项")
    public JsonResult getInfo(@PathVariable String mId){
        JsonResult result=new JsonResult();
        SysMenu menu = sysMenuService.getById(mId);
        if(null==menu){
            log.info("找不到菜单项：id={}",mId);
            result.setFail("找不到角色:id="+mId);
        }else {
            result.setData(menu);
        }

        return result;
    }
}
