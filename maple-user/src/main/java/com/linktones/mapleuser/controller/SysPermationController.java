package com.linktones.mapleuser.controller;


import com.linktones.mapleuser.bean.JsonResult;
import com.linktones.mapleuser.entity.SysPermation;
import com.linktones.mapleuser.service.ISysPermationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author maple
 * @since 2020-12-31
 */
@RestController
@RequestMapping("/sys-permation")
public class SysPermationController {

    @Autowired
    private ISysPermationService sysPermationService;

    @GetMapping("f01")
    @ApiOperation(value = "获取权限列表",response = JsonResult.class)
    public JsonResult<SysPermation> getList(){
        JsonResult<SysPermation> result=new JsonResult<>();
        result.setListSuccess(sysPermationService.list());
        return result;
    }


}
