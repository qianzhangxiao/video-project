package com.qzx.user.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* 
* @TableName vo_user_role
*/
@TableName("vo_user_role")
@Data
public class VoUserRoleEntity implements Serializable {

    /**
    * 
    */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 用户ID
    */
    private Long userId;


}
