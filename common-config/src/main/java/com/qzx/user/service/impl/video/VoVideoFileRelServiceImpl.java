package com.qzx.user.service.impl.video;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzx.user.entity.video.VoVideoFileRelEntity;
import com.qzx.user.mapper.video.IVoVideoFileRelMapper;
import com.qzx.user.service.video.IVoVideoFileRelService;
import org.springframework.stereotype.Service;

@Service
public class VoVideoFileRelServiceImpl
        extends ServiceImpl<IVoVideoFileRelMapper, VoVideoFileRelEntity>
        implements IVoVideoFileRelService {
}
