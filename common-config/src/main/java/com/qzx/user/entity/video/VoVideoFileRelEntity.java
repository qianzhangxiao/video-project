package com.qzx.user.entity.video;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("vo_video_file_rel")
@Data
public class VoVideoFileRelEntity implements Serializable {
    private static final long serialVersionUID = 7701542755342300325L;

    @TableId
    private Long id;

    private Long fileId;

    private Long videoId;

}
