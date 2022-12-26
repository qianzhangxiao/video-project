package com.qzx.user.service;

import com.qzx.user.utils.ResponseResult;

public interface IEmailService {

    ResponseResult<?> sendEmailCode(String emailCode,String to);

    ResponseResult<?> activityAccount(String userCode, String to);

    ResponseResult<?> addUser();
}
