package com.qzx.user.video.service;

import com.qzx.user.entity.video.VoVideoInfoEntity;
import com.qzx.user.utils.ResponseResult;

import java.util.List;

public interface IVideoService {

    ResponseResult<?> saveVideoInfo(VoVideoInfoEntity videoInfo);

    ResponseResult<?> deleteVideoInfo(List<Long> ids);
}
