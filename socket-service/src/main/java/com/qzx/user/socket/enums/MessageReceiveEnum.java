package com.qzx.user.socket.enums;

public enum MessageReceiveEnum {

    TO_ONE(1,"单聊"),
    TO_GROUP(2,"群组"),
    TO_ALL(3,"所有人");

    private Integer type;

    private String desc;

    MessageReceiveEnum(Integer type,String desc){
        this.type = type;
        this.desc = desc;
    }

    public Integer getType(){
        return this.type;
    }

    public String getDesc(){
        return this.desc;
    }

}
