package com.qzx.user.video.service.impl;

import com.qzx.user.entity.video.VoVideoInfoEntity;
import com.qzx.user.service.video.IVoVideoFileRelService;
import com.qzx.user.service.video.IVoVideoInfoService;
import com.qzx.user.utils.ResponseResult;
import com.qzx.user.video.service.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return null;
    }
}
