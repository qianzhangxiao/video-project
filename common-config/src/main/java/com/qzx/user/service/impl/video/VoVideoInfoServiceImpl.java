package com.qzx.user.service.impl.video;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzx.user.entity.video.VoVideoInfoEntity;
import com.qzx.user.mapper.video.IVoVideoInfoMapper;
import com.qzx.user.service.video.IVoVideoInfoService;
import org.springframework.stereotype.Service;

@Service
public class VoVideoInfoServiceImpl
    extends ServiceImpl<IVoVideoInfoMapper, VoVideoInfoEntity>
    implements IVoVideoInfoService {
}
