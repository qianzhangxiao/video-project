package com.qzx.user.conntroller;

import com.qzx.user.dto.EmailInfo;
import com.qzx.user.dto.LoginUser;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.service.ILoginService;
import com.qzx.user.utils.Constant;
import com.qzx.user.utils.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/author")
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class LoginController {

    private final ILoginService loginService;

    @GetMapping("/getCaptcha")
    public ResponseResult<?> getCaptcha(){
        return loginService.getCaptcha();
    }

    @GetMapping("/sendEmailCode")
    public ResponseResult<?> sendEmailCode(String emailAddress){
        return loginService.sendEmailCode(emailAddress);
    }

    @GetMapping("/checkUserCode")
    public ResponseResult<?> checkUserCode(String userCode){
        return loginService.checkUserCode(userCode);
    }

    @GetMapping("/checkEmail")
    public ResponseResult<?> checkEmail(String email){
        return loginService.checkEmail(email);
    }

    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody VoUserEntity user){
        return loginService.register(user);
    }

    @PostMapping("/loginByPassword")
    public ResponseResult<?> loginByPassword(@RequestBody LoginUser loginUser){
        return loginService.loginByPassword(loginUser);
    }

    @GetMapping("/loginByEmailCode")
    public ResponseResult<?> loginByEmailCode(@RequestBody LoginUser loginUser){
        return loginService.loginByEmailCode(loginUser);
    }
    /**
     * 激活账号
      */
    @GetMapping("/activeAccount/{userCode}")
    public ResponseResult<?> activeAccount(@PathVariable("userCode") String userCode){
        return loginService.activeAccount(userCode);
    }

    @GetMapping("/logout")
    public void logout(@RequestHeader(value = Constant.TOKEN_DESC,required = true)String token ){
        loginService.logout(token);
    }

    @GetMapping("/testSeata")
    public ResponseResult<?>  testSeata(){
        return loginService.testSeata();
    }

    @GetMapping("/sendEmail")
    public ResponseResult<?> sendEmail(@RequestParam("file")MultipartFile[] files, EmailInfo emailInfo){
        return loginService.sendEmail(files,emailInfo);
    }
}
