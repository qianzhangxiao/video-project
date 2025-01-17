package com.qzx.user.entity.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞表
 */
@Data
@TableName("vo_video_like")
public class VoVideoLikeEntity implements Serializable {

    private static final long serialVersionUID = -2924095771949853535L;

    @TableId
    private Long id;

    /**
     * 视频主键
     */
    private Long videoId;

    /**
     * 点赞人
     */
    private String likeUser;

    /**
     * 点赞时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date likeTime;
}
