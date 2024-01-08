package com.qzx.user.entity.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qzx.user.entity.VoFileEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("vo_video_info")
public class VoVideoInfoEntity implements Serializable {

    private static final long serialVersionUID = 5835870475854853992L;

    @TableId
    private Long videoId;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频封面
     */
    private Long videoPicture;

    /**
     * 视频描述
     */
    private String videoDesc;

    /**
     * 视频分类
     */
    private String videoType;

    /**
     * 视频时长
     */
    private Long videoDuration;

    /**
     * 视频状态
     */
    private Byte videoStatus;

    /**
     * 上传时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 上传的附件列表
     */
    @TableField(exist = false)
    private List<VoFileEntity> fileList;
}
