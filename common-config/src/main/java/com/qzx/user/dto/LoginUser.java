package com.qzx.user.dto;

import lombok.Data;

@Data
public class LoginUser {

    private String userName;
    private String password;
    private String emailCodeId;
    private String emailCode;
    private String captchaId;
    private String captcha;
    private Integer rememberMe;

}
