package com.qzx.user.utils;


import java.util.Locale;
import java.util.UUID;

public class UUIDUtils {

    public static String getTokenUp(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
    public static String getTokenLower(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase(Locale.ROOT);
    }
    public static String getCode(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static String getLowerCode(){
        return UUID.randomUUID().toString().toLowerCase();
    }
}
