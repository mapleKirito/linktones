package com.linktones.mapleuser.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linktones.mapleuser.bean.JsonResult;
import com.linktones.mapleuser.entity.SysPermation;
import com.linktones.mapleuser.service.ISysPermationService;
import com.linktones.mapleuser.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author maple
 * @since 2020-12-31
 */
@RestController
@RequestMapping("/permations")
public class SysPermationController {

    @Autowired
    private ISysPermationService sysPermationService;

    @Autowired
    private JsonUtils jsonUtils;

    @GetMapping("list")
    @ApiOperation(value = "获取权限列表",response = JsonResult.class)
    public JsonResult getList(){
        JsonResult result=new JsonResult();
        result.setList(sysPermationService.list());
        return result;
    }

    @PostMapping("init")
    @ApiOperation(value = "权限初始化",response = JSONObject.class)
    public JsonResult initPermation(@RequestBody JSONObject jsonObject){
        JsonResult result=new JsonResult();
        //参数校验
        if(!jsonUtils.checkParamNull(jsonObject)){
            result.setFail("接口入参格式不正确");
            return result;
        }
        //获取数据
        String moduleName = jsonObject.getString("moduleName");
        JSONArray list = jsonObject.getJSONArray("list");
        List<SysPermation> permationList = list.toJavaList(SysPermation.class);
        sysPermationService.initPermation(permationList,moduleName);
        result.setSuccess("初始化权限完成");
        return result;
    }


}
