package com.linktones.mapleuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linktones.mapleuser.entity.SysPermation;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author maple
 * @since 2020-12-31
 */
public interface ISysPermationService extends IService<SysPermation> {

    /**
     * 初始化权限
     * @param list 扫描到的权限清单
     */
    public void initPermation(List<SysPermation> list,String moduleName);

}
