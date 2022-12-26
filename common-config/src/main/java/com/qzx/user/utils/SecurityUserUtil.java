package com.qzx.user.utils;

import com.qzx.user.entity.VoUserEntity;

public class SecurityUserUtil {

    private static final ThreadLocal<VoUserEntity> threadLocal = new ThreadLocal<>();

    public static void setUserInfo(VoUserEntity user){
        threadLocal.set(user);
    }

    public static VoUserEntity getUser(){
        return threadLocal.get();
    }

}
