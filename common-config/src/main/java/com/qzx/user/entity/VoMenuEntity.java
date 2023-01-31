package com.qzx.user.entity;


import java.io.Serializable;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 
* @TableName vo_menu
*/
@Data
@TableName("vo_menu")
public class VoMenuEntity implements Serializable {

    private static final long serialVersionUID = -8502285623599868029L;
    /**
    * 
    */
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Long menuId;
    /**
    * 
    */
    private Long menuParentId;
    /**
    * 菜单名称
    */
    private String menuName;
    /**
    * 菜单类型
    */
    private String menuType;
    /**
    * 菜单图标
    */
    private String menuIcon;
    /**
    * 菜单排序
    */
    private Integer menuSort;
    /**
    * 路由地址
    */
    private String routerUrl;
    /**
    * 组件路径
    */
    private String component;
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
    private Date updateTime;
    /**
    * 状态
    */
    private Integer menuStatus;

}
