package com.qzx.user.video.controller;

import com.qzx.user.entity.video.VoVideoInfoEntity;
import com.qzx.user.utils.ResponseResult;
import com.qzx.user.video.service.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videoInfo")
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class VideoController {

    private final IVideoService videoService;

    /**
     * 上传视频
     */
    @PostMapping("/saveVideoInfo")
    public ResponseResult<?> saveVideoInfo(@RequestBody VoVideoInfoEntity videoInfo){
        return videoService.saveVideoInfo(videoInfo);
    }
    /**
     * 删除视频
     */

    /**
     * 查询个人视频列表
     */

    /**
     * 查询公共视频
     */

    /**
     * 分享私有视频给好友
     */

    /**
     * 分享私有视频到公共视频（需审核）
     */


}
