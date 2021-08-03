package com.linktones.mapleuser.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author maple
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel(value="SysMenu对象", description="菜单表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    @TableId(value = "m_id", type = IdType.ASSIGN_UUID)
    private String mId;

    @ApiModelProperty(value = "菜单序号：1,1-1等")
    private String mIndex;

    @ApiModelProperty(value = "菜单父节点序号")
    private String mParentIndex;

    @ApiModelProperty(value = "菜单名称")
    private String mName;

    @ApiModelProperty(value = "菜单图标：element UI 图标样式")
    private String mIcon;

    @ApiModelProperty(value = "是否包含子菜单")
    private Boolean mHasChildren;

    @ApiModelProperty(value = "菜单跳转url")
    private String mUrl;

    @ApiModelProperty(value = "关联角色id")
    private String mRoleId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private String updateTime;


}
