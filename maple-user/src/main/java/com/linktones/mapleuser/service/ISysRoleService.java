package com.linktones.mapleuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linktones.mapleuser.bean.PageVo;
import com.linktones.mapleuser.entity.SysRole;
import com.linktones.mapleuser.entity.SysRolePermationMapper;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author maple
 * @since 2021-01-16
 */
public interface ISysRoleService extends IService<SysRole> {

    public boolean bindPermation(SysRolePermationMapper rolePermationMapper);
    public boolean unbindPermation(SysRolePermationMapper rolePermationMapper);
    public PageVo getPage(int pageNum, int pageSize, String roleName);

}
