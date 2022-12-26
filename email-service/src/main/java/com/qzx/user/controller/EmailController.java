package com.qzx.user.controller;

import com.qzx.user.service.IEmailService;
import com.qzx.user.utils.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmailController {

    private final IEmailService emailService;

    @GetMapping("/sendEmailCode")
    public ResponseResult<?> sendEmailCode(String emailCode,String to){
        return emailService.sendEmailCode(emailCode,to);
    }

    @GetMapping("/activityAccount")
    public ResponseResult<?> activityAccount(String userCode,String to){
        return emailService.activityAccount(userCode,to);
    }

    @GetMapping("/addUser")
    public ResponseResult<?> addUser(){
        return emailService.addUser();
    }
}
