package com.qzx.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzx.user.exception.BusinessException;
import com.qzx.user.utils.ResponseResult;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.service.IVoUserService;
import com.qzx.user.mapper.IUserNormalMapper;
import com.qzx.user.service.IUserNormalService;
import com.qzx.user.utils.SecurityUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
public class UserNormalServiceImpl  implements IUserNormalService {

    @Resource
    private IVoUserService userService;

    @Resource
    private IUserNormalMapper userNormalMapper;

    @Override
    public ResponseResult<?> queryUserInfo() {
        final VoUserEntity user = SecurityUserUtil.getUser();
        VoUserEntity newUser = new VoUserEntity();
        BeanUtils.copyProperties(user,newUser);
        newUser.setRoleList(null);
        return ResponseResult.success(newUser);
    }

    @Override
    public ResponseResult<?> queryUsers() {
        IPage<VoUserEntity> page=userService.page(new Page<>());
        return ResponseResult.success(page);
    }

    @Override
    public ResponseResult<?> saveUser(VoUserEntity user) {
        boolean save = userService.save(user);
        if (!save){
            return ResponseResult.error(500,"数据插入失败");
        }
        return ResponseResult.success(user);
    }
}
