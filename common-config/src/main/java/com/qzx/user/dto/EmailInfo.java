package com.qzx.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @description:邮件信息对象
 * @author: qc
 * @time: 2020/8/19 10:21
 */
@Data
public class EmailInfo implements Serializable {

    private String from;  //谁发送,默认为此邮箱
    private String to;  //发送给谁
    private String template; //模板名称(复杂邮件发送，html的名称)
    private String subject; // 主题
    private String text;    //发送内容
    private Map<String,Object> variable; //传递模板引擎需要的参数
}
