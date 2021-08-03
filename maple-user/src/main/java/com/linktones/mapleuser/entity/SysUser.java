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
 * 
 * </p>
 *
 * @author maple
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="SysUser对象", description="")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "USER_ID", type = IdType.ASSIGN_UUID)
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "账号")
    @TableField("USER_ACCOUNT")
    private String userAccount;

    @ApiModelProperty(value = "密码")
    @TableField("USER_PASSWORD")
    private String userPassword;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME",fill = FieldFill.INSERT_UPDATE)
    private String updateTime;


}
