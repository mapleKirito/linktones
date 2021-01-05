package com.linktones.mapleuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linktones.mapleuser.entity.SysPermation;
import com.linktones.mapleuser.mapper.SysPermationMapper;
import com.linktones.mapleuser.service.ISysPermationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author maple
 * @since 2020-12-31
 */
@Service
@Slf4j
public class SysPermationServiceImpl extends ServiceImpl<SysPermationMapper, SysPermation> implements ISysPermationService {

    /**
     * 初始化权限
     * @param list 扫描到的权限清单
     */
    public void initPermation(List<SysPermation> list,String moduleName){
        QueryWrapper<SysPermation> wrapper=new QueryWrapper<>();
        //获取权限表原有内容，用来判断是否删除
        wrapper.lambda().eq(SysPermation::getPermissionModule,moduleName);
        List<String> urlList=list.stream().map(SysPermation::getPermissionUrl).collect(Collectors.toList());
        wrapper.lambda().notIn(SysPermation::getPermissionUrl,urlList);
        List<SysPermation> beforeList = list(wrapper);
        //去除重复项，获取要清理的项,判断依据URL+moduleName
        log.info("废弃列表项:{}",beforeList);
        //处理废弃权限
        abandonedPermation(beforeList);

        //遍历，判断数据库是否有记录
        for (SysPermation sysPermation : list) {
            wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SysPermation::getPermissionUrl,sysPermation.getPermissionUrl());
            wrapper.lambda().eq(SysPermation::getPermissionModule,sysPermation.getPermissionModule());
            if(saveOrUpdate(sysPermation,wrapper)){
                log.info("权限[{}],url=>{},初始化完成",sysPermation.getPermissionDesc(),sysPermation.getPermissionUrl());

            }else {
                log.error("权限[{}],url=>{},初始化失败",sysPermation.getPermissionDesc(),sysPermation.getPermissionUrl());
            }


        }
    }



    /**
     * 处理废弃权限
     * @param list 废弃权限列表
     */
    private void abandonedPermation(List<SysPermation> list){
        //判断是否有角色在用
        for (SysPermation sysPermation : list) {
            log.info("要移除的权限内容:{}",sysPermation.toString());
            if(getBaseMapper().checkPermationIsUse(sysPermation.getPermissionId())<=0){
                log.info("要移除的权限内容:{}",sysPermation.toString());
                //没有使用
                if(removeById(sysPermation)){
                    log.info("移除废弃的权限[{}],url=>{},成功",sysPermation.getPermissionDesc(),sysPermation.getPermissionUrl());
                }else {
                    log.error("移除废弃的权限[{}],url=>{},失败",sysPermation.getPermissionDesc(),sysPermation.getPermissionUrl());
                }

            }else{
                //在使用,标记为不可用
                sysPermation.setPermissionEnable(0);
                if(updateById(sysPermation)){
                    log.info("标记废弃的权限[{}],url=>{},成功",sysPermation.getPermissionDesc(),sysPermation.getPermissionUrl());
                }else {
                    log.error("标记废弃的权限[{}],url=>{},失败",sysPermation.getPermissionDesc(),sysPermation.getPermissionUrl());
                }
            }
        }
    }
}
