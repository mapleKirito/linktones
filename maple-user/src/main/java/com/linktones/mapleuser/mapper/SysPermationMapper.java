package com.linktones.mapleuser.mapper;

import com.linktones.mapleuser.entity.SysPermation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2020-12-31
 */
public interface SysPermationMapper extends BaseMapper<SysPermation> {

    //判断权限是否被使用
    public Integer checkPermationIsUse(String permationId);
}
