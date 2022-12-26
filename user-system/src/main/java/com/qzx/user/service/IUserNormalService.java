package com.qzx.user.service;


import com.qzx.user.utils.ResponseResult;
import com.qzx.user.entity.VoUserEntity;

public interface IUserNormalService {


    ResponseResult<?> queryUserInfo();


    ResponseResult<?> queryUsers();

    ResponseResult<?> saveUser(VoUserEntity user);
}
