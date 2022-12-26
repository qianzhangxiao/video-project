package com.qzx.user.video.service.impl;

import com.qzx.user.video.mapper.IVideoMapper;
import com.qzx.user.video.service.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class VideoServiceImpl implements IVideoService {

    private final IVideoMapper videoMapper;

}
