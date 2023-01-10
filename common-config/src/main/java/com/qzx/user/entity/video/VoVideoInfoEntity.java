package com.qzx.user.entity.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("vo_video_info")
public class VoVideoInfoEntity implements Serializable {

    @TableId(type = IdType.AUTO)
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
     * 播放次数
     */
    private Long playCount;

    /**
     * 视频时长
     */
    private Integer videoDuration;

    /**
     * 视频状态
     */
    private Byte videoStatus;
}
