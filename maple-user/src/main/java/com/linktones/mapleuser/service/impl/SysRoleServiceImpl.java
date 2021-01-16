package com.linktones.mapleuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linktones.mapleuser.bean.PageVo;
import com.linktones.mapleuser.entity.SysRole;
import com.linktones.mapleuser.entity.SysRolePermationMapper;
import com.linktones.mapleuser.mapper.SysRoleMapper;
import com.linktones.mapleuser.service.ISysRolePermationMapperService;
import com.linktones.mapleuser.service.ISysRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author maple
 * @since 2021-01-16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRolePermationMapperService rolePermationService;

    //权限绑定
    @Override
    public boolean bindPermation(SysRolePermationMapper rolePermationMapper) {
        return rolePermationService.save(SysRolePermationMapper.builder()
                .roleId(rolePermationMapper.getRoleId())
                .permationId(rolePermationMapper.getPermationId())
                .build());
    }

    //权限解绑
    @Override
    public boolean unbindPermation(SysRolePermationMapper rolePermationMapper) {
        QueryWrapper<SysRolePermationMapper> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRolePermationMapper::getRoleId,rolePermationMapper.getRoleId());
        queryWrapper.lambda().eq(SysRolePermationMapper::getPermationId,rolePermationMapper.getPermationId());
        return rolePermationService.remove(queryWrapper);
    }

    //分页查询
    @Override
    public PageVo getPage(int pageNum, int pageSize, String roleName) {
        Page<SysRole> page=new Page<>(pageNum,pageSize);
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isBlank(roleName)){
            queryWrapper.lambda().like(SysRole::getRoleName,roleName);
        }
        Page<SysRole> sysRolePage = getBaseMapper().selectPage(page, queryWrapper);
        return PageVo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(sysRolePage.getTotal())
                .list(sysRolePage.getRecords())
                .build();
    }
}
