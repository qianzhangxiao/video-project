package com.qzx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qzx.user.dto.JwtUserDetails;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.exception.BusinessException;
import com.qzx.user.mapper.VoRoleMapper;
import com.qzx.user.service.IUserDetailService;
import com.qzx.user.service.IVoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class UserDetailServiceImpl implements IUserDetailService {

    private final IVoUserService voUserService;

    private final VoRoleMapper voRoleMapper;

    /**根据账号或邮箱查询用户信息*/
    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        VoUserEntity voUser = userCode.contains("@")?voUserService.getOne(new LambdaQueryWrapper<VoUserEntity>(){{
            eq(VoUserEntity::getEmail,userCode);
            eq(VoUserEntity::getStatus,3);
        }}):voUserService.getOne(new LambdaQueryWrapper<VoUserEntity>(){{
            eq(VoUserEntity::getUserCode,userCode);
            eq(VoUserEntity::getStatus,3);
        }});
        if (ObjectUtils.isEmpty(voUser)){
            throw new BusinessException("用户未激活或不存在");
        }
        /**查询权限*/
        voUser.setRoleList(voRoleMapper.queryRoleList(voUser.getId()));
        return new JwtUserDetails(voUser);
    }
}
