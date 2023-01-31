package com.qzx.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzx.user.entity.VoFileEntity;
import com.qzx.user.mapper.IVoFileMapper;
import org.springframework.stereotype.Service;

@Service
public class VoFileServiceImpl
    extends ServiceImpl<IVoFileMapper, VoFileEntity>
    implements IVoFileService{
}
