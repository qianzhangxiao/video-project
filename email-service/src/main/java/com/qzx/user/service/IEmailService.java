package com.qzx.user.service;

import com.qzx.user.dto.EmailInfo;
import com.qzx.user.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface IEmailService {

    ResponseResult<?> sendEmailCode(String emailCode,String to);

    ResponseResult<?> activityAccount(String userCode, String to);

    ResponseResult<?> addUser();

    ResponseResult<?> sendEmailWithFile(MultipartFile[] files, EmailInfo emailInfo);
}
