package com.qzx.user.service.impl;

import com.alibaba.fastjson2.JSON;
import com.qzx.user.utils.Constant;
import com.qzx.user.dto.CustomAuthenticationToken;
import com.qzx.user.dto.JwtUserDetails;
import com.qzx.user.exception.BusinessException;
import com.qzx.user.service.IUserDetailService;
import com.qzx.user.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final IUserDetailService detailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RedisUtil redisUtil;

    /**
     * 自定义登录
     * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthenticationToken authenticationToken = (CustomAuthenticationToken) authentication;
        return authenticationToken.getType()==1?loginByPassword(authenticationToken):loginByEmail(authenticationToken);
    }

    private Authentication loginByPassword(CustomAuthenticationToken authenticationToken){
        final JwtUserDetails userDetails = (JwtUserDetails)detailService.loadUserByUsername(authenticationToken.getName());
        if (ObjectUtils.isEmpty(userDetails)){
            throw new BusinessException("用户信息不存在");
        }
        if (ObjectUtils.isEmpty(authenticationToken.getCredentials())){
            throw new BusinessException("密码不能为空");
        }
        String password = authenticationToken.getCredentials().toString();
        if (!bCryptPasswordEncoder.matches(password,userDetails.getPassword())){
            throw new BusinessException("密码有误");
        }
        redisUtil.set(Constant.USER_LOGIN_TOKEN+userDetails.getUsername(), JSON.toJSONString(userDetails.getUser()),60*30);
        return new CustomAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }

    private Authentication loginByEmail(CustomAuthenticationToken authenticationToken){
        final JwtUserDetails userDetails = (JwtUserDetails)detailService.loadUserByUsername(authenticationToken.getName());
        if (ObjectUtils.isEmpty(userDetails)){
            throw new BusinessException("用户信息不存在");
        }
        /**
         * 保存用户信息至redis，无操作后30分钟删除，有操作重新赋值30分钟
         * */
        redisUtil.set(Constant.USER_LOGIN_TOKEN+userDetails.getUsername(), JSON.toJSONString(userDetails.getUser()),60*30);
        return new CustomAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthenticationToken.class);
    }
}
