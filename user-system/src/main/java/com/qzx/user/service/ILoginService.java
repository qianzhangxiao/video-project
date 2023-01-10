package com.qzx.user.service;

import com.qzx.user.dto.EmailInfo;
import com.qzx.user.dto.LoginUser;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface ILoginService {

    ResponseResult<?> getCaptcha();

    ResponseResult<?> sendEmailCode(String emailAddress);

    ResponseResult<?> loginByPassword(LoginUser loginUser);

    ResponseResult<?> loginByEmailCode(LoginUser loginUser);

    ResponseResult<?> register(VoUserEntity user);

    ResponseResult<?> checkUserCode(String userCode);

    ResponseResult<?> checkEmail(String email);

    ResponseResult<?> activeAccount(String userCode);

    void logout(String token);

    ResponseResult<?> testSeata();

    ResponseResult<?> sendEmail(MultipartFile[] files, EmailInfo emailInfo);
}
