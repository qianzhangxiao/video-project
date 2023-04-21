package com.qzx.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("vo_file")
public class VoFileEntity implements Serializable {

    private static final long serialVersionUID = -6814760381616736062L;

    @TableId
    private Long fileId;

    /**
     *附件名称
     */
    private String fileName;

    /**
     *附件路径
     */
    private String filePath;

    /**
     * 附件完整路径
     */
    private String filePathName;
    /**
     *附件大小（kb)
     */
    private Long fileSize;

    /**
     *附件后缀
     */
    private String fileSuffix;

    /**
     *文件类型
     */
    private String fileType;

    /**
     *原始文件名称
     */
    private String fileOriginalName;

    /**
     *上传时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uploadTime;

    /**
     *上传者
     */
    private String uploadPerson;

    /**
     *文件状态
     * 0：删除，1：正常
     */
    private Byte fileStatus;

    /**
     * 文件md5
     */
    private String fileGuid;

}
