package com.qzx.user.sentinel;


import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.utils.ResponseResult;

import java.sql.SQLException;

/**
 * fallback管理的是java运行异常
 * */
public class SentinelFallbackHandle {


    // 服务熔断降级处理，函数签名与原函数一致或加一个 Throwable 类型的参数
    public static ResponseResult<?> getCaptchaByNameFallback(Throwable throwable) {
        System.out.println("selectUserByNameFallback异常信息：" + throwable.getMessage());
        return handleFallback(throwable);
    }

    // 服务熔断降级处理，函数签名与原函数一致或加一个 Throwable 类型的参数
    public static ResponseResult<?> registerFallback(VoUserEntity user, Throwable throwable) {
        System.out.println("selectUserByNameFallback异常信息：" + throwable.getMessage());
        return handleFallback(throwable);
    }

    private static ResponseResult<?> handleFallback(Throwable throwable){
        if (throwable instanceof SQLException){
            return ResponseResult.error(500,"数据异常，请联系管理员");
        }
        return ResponseResult.error(500,"系统异常，请联系管理员");
    }
}
