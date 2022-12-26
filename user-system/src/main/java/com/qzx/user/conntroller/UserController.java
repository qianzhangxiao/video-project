package com.qzx.user.conntroller;

import com.qzx.user.utils.ResponseResult;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.service.IUserNormalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class UserController {

    private final IUserNormalService userNormalService;

    @GetMapping("/queryUserInfo")
    public ResponseResult<?> queryUserInfo(){
        return userNormalService.queryUserInfo();
    }

    @GetMapping("/queryUsers")
    public ResponseResult<?> queryUsers(){
        return userNormalService.queryUsers();
    }

    @PostMapping("/saveUser")
    public ResponseResult<?> saveUser(@RequestBody VoUserEntity user){
        return userNormalService.saveUser(user);
    }



}
