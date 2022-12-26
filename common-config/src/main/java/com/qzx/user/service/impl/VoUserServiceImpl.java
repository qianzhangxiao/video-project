package com.qzx.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.mapper.IVoUserMapper;
import com.qzx.user.service.IVoUserService;
import org.springframework.stereotype.Service;

@Service
public class VoUserServiceImpl extends ServiceImpl<IVoUserMapper, VoUserEntity> implements IVoUserService {

}
