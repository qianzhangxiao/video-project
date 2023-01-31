package com.qzx.user.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
* 
* @TableName vo_role
*/
@Data
@TableName("vo_role")
public class VoRoleEntity implements Serializable {

    private static final long serialVersionUID = 5229388168023768064L;
    /**
    * 权限表ID
    */
    @TableId(value = "role_id",type = IdType.AUTO)
    private Long roleId;
    /**
    * 角色编码
    */
    private String roleCode;
    /**
    * 角色名称
    */
    private String roleName;
    /**
    * 创建人
    */
    private String createBy;
    /**
    * 创建时间
    */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
    * 修改人
    */
    private String updateBy;
    /**
    * 修改时间
    */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    /**
    * 角色状态，0：删除，1：在用
    */
    private Integer roleStatus;

    @TableField(exist = false)
    private List<VoMenuEntity> menuList;
}
