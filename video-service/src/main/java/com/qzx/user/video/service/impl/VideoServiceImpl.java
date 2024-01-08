package com.qzx.user.video.service.impl;

import com.qzx.user.entity.video.VoVideoFileRelEntity;
import com.qzx.user.entity.video.VoVideoInfoEntity;
import com.qzx.user.service.video.IVoVideoFileRelService;
import com.qzx.user.service.video.IVoVideoInfoService;
import com.qzx.user.utils.ResponseResult;
import com.qzx.user.video.service.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class VideoServiceImpl implements IVideoService {

    private final IVoVideoInfoService voVideoInfoService;

    private final IVoVideoFileRelService voVideoFileRelService;

    @Override
    public ResponseResult<?> saveVideoInfo(VoVideoInfoEntity videoInfo) {
        /**
         * 保存视频信息
         */
        videoInfo.setVideoDuration(videoInfo.getFileList().get(0).getFileDuration());
        if (voVideoInfoService.save(videoInfo)){
            voVideoFileRelService.save(new VoVideoFileRelEntity(){{
                setVideoId(videoInfo.getVideoId());
                setFileId(videoInfo.getFileList().get(0).getFileId());
            }});
        }
        return ResponseResult.success("视频保存成功");
    }

    @Override
    public ResponseResult<?> deleteVideoInfo(List<Long> ids) {
        return null;
    }
}
