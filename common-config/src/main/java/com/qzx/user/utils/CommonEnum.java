package com.qzx.user.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommonEnum {

    public enum UserStatusEnum{

        DELETE_ACCOUNT(0,"删除"),
        DISABLE_ACCOUNT(1,"禁用"),
        NO_ACTIVITY_ACCOUNT(2,"未激活"),
        ACTIVITY_ACCOUNT(3,"正常");

        private Integer code;

        private String msg;

        UserStatusEnum(Integer code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public String getMsg(){
            return this.msg;
        }

        public Byte getCode(){
            return this.code.byteValue();
        }

        public static Byte getCodeByMsg(String msg){
            return Arrays.stream(UserStatusEnum.values()).filter(res -> Objects.equals(msg, res.getMsg())).map(UserStatusEnum::getCode).collect(Collectors.toList()).get(0);
        }

        public static String getMsgByCode(Integer code){
            return Arrays.stream(UserStatusEnum.values()).filter(res -> Objects.equals(code.byteValue(), res.getCode())).map(UserStatusEnum::getMsg).collect(Collectors.toList()).get(0);
        }

    }

}
