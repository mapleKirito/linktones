package com.linktones.mapleuser.controller;


import com.alibaba.fastjson.JSONObject;
import com.linktones.mapleuser.bean.JsonResult;
import com.linktones.mapleuser.bean.PageVo;
import com.linktones.mapleuser.entity.SysRole;
import com.linktones.mapleuser.entity.SysRolePermationMapper;
import com.linktones.mapleuser.service.ISysRoleService;
import com.linktones.mapleuser.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author maple
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/roles")
@Slf4j
@Api("角色接口")
public class SysRoleController {
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private JsonUtils jsonUtils;

    @PostMapping("bind")
    @ApiOperation(value = "权限绑定")
    public JsonResult bindPermation(@RequestBody SysRolePermationMapper rolePermationMapper){
        JsonResult result=new JsonResult();
        if(!roleService.bindPermation(rolePermationMapper)){
            result.setFail("绑定权限失败");
        }

        return result;
    }

    @PostMapping("unbind")
    @ApiOperation(value = "权限解绑")
    public JsonResult unbindPermation(@RequestBody SysRolePermationMapper rolePermationMapper){
        JsonResult result=new JsonResult();
        if(!roleService.unbindPermation(rolePermationMapper)){
            result.setFail("解绑权限失败");
        }

        return result;
    }

    @PostMapping("list")
    @ApiOperation(value = "获取列表")
    public JsonResult getList(@ApiParam("pageNum页码数,pageSize每页长度,roleName角色名筛选非必填") @RequestBody JSONObject jsonObject){
        if(!jsonUtils.checkParamNull(jsonObject,"pageNum","pageSize")){
            return new JsonResult(-1,"分页参数不正确");
        }


        PageVo pageVo = roleService.getPage(jsonObject.getInteger("pageNum")
                ,jsonObject.getInteger("pageSize"),jsonObject.getString("roleName"));
        JsonResult result=new JsonResult();
        result.setData(pageVo);

        return result;
    }

    /**
     *
     * @param roleId 资源ID
     * @return
     */
    @GetMapping("/{roleId}")
    @ApiOperation(value = "查看角色")
    public JsonResult getInfo(@PathVariable String roleId){
        JsonResult result=new JsonResult();
        SysRole role = roleService.getById(roleId);
        if(null==role){
            log.info("找不到角色：id={}",roleId);
            result.setFail("找不到角色:id="+roleId);
        }else {
            result.setData(role);
        }

        return result;
    }

    /**
     *
     * @param roleId 资源ID
     * @param role 变更内容
     * @return
     */
    @PutMapping("/{roleId}")
    @ApiOperation(value = "更新角色")
    public JsonResult modify(@PathVariable String roleId,@RequestBody SysRole role){
        JsonResult result=new JsonResult();
        role.setRoleId(roleId);
        if(!roleService.updateById(role)){
            result.setFail("更新失败");
        }

        return result;
    }

    /**
     *
     * @param role 资源内容
     * @return
     */
    @PostMapping()
    @ApiOperation(value = "添加角色")
    public JsonResult add(@RequestBody SysRole role){
        JsonResult result=new JsonResult();
        if(!roleService.save(role)){
            result.setFail("添加失败");
        }

        return result;
    }

    @DeleteMapping("/{roleId}")
    @ApiOperation(value = "删除角色")
    public JsonResult delete(@PathVariable String roleId){
        JsonResult result=new JsonResult();
        if(!roleService.removeById(roleId)){
            result.setFail("删除失败");
        }

        return result;
    }

}
