package com.qzx.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("vo_user")
public class VoUserEntity implements Serializable {

    /**
     * 将该属性对应的字段指定为主键
     * TableId注解的value属性用于指定主键的字段
     * TableId注解的type属性用于设置主键的生成策略（一定要开启自增）最终插入的主键不再是雪花算法生成的
     * */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 用户编码
     * */
    private String userCode;

    /**
     * 用户昵称
     * */
    private String userName;

    /**
     * 用户密码
     * */
    private String password;

    /**
     * 用户邮箱
     * */
    private String email;

    /**
     * 用户号码
     * */
    private String phone;
    /**
     * 用户性别
     * */
    private String gender;
    /**
     * 用户状态，0：删除，1：封禁，2：注册待验证，3：正常
     * */
    private Byte status;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<VoRoleEntity> roleList;
}
