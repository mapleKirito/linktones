package com.linktones.mapleuser.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author maple
 * @since 2020-12-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permation")
@ApiModel(value="SysPermation对象", description="权限表")
public class SysPermation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限id")
    @TableId(value = "permission_id", type = IdType.ASSIGN_UUID)
    private String permissionId;

    @ApiModelProperty(value = "权限地址")
    private String permissionUrl;

    @ApiModelProperty(value = "权限描述")
    private String permissionDesc;

    @ApiModelProperty(value = "权限模块")
    private String permissionModule;

    @ApiModelProperty(value = "权限可用状态:1可用/0不可用")
    private Integer permissionEnable;

    @ApiModelProperty(value = "创建时间 2020-12-31 14:37:04")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "更新时间 2020-12-31 14:37:22")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private String updateTime;


}
