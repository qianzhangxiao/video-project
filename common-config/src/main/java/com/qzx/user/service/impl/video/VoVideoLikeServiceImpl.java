package com.qzx.user.service.impl.video;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzx.user.entity.video.VoVideoLikeEntity;
import com.qzx.user.mapper.video.IVoVideoLikeMapper;
import com.qzx.user.service.video.IVoVideoLikeService;
import org.springframework.stereotype.Service;

@Service
public class VoVideoLikeServiceImpl
    extends ServiceImpl<IVoVideoLikeMapper, VoVideoLikeEntity>
    implements IVoVideoLikeService {
}
