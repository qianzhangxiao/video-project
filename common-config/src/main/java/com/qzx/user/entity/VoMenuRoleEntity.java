package com.qzx.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 
* @TableName vo_menu_role
*/
@Data
@TableName("vo_menu_role")
public class VoMenuRoleEntity implements Serializable {

    private static final long serialVersionUID = -452016626842180604L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 菜单ID
    */
    private Long menuId;


}
