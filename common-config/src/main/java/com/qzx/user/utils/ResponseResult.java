package com.qzx.user.utils;


import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    private ResponseResult() {
        this.code = 200;
        this.msg = "success";
        this.data = null;
    }

    private ResponseResult(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    private ResponseResult(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    private ResponseResult(ResultEnum resultEnum) {
        if (resultEnum != null) {
            this.code = resultEnum.getCode();
            this.msg = resultEnum.getMsg();
        }
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(data);
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>();
    }

    public static <T> ResponseResult<T> error(ResultEnum resultEnum) {
        return new ResponseResult<>(resultEnum);
    }
    public static <T> ResponseResult<T> error(int code,String msg) {
        return new ResponseResult<>(code,msg);
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }


    public enum ResultEnum {

        NO_USER(500, "用户不存在"),
        FILE_OVERSIZE(500,"文件大小不能超过限制"),
        FILE_ERROR(500,"文件上传异常"),
        NUll_ERROR(500,"数据有误"),
        NONE_USER_CODE(500, "账号不能为空"),
        NONE_USER_PASSWORD(500,"密码不能为空"),
        NONE_USER_EMAIL(500,"邮箱不能为空"),
        NONE_USER_NAME(500,"用户名不能为空"),
        REPEAT_USER_CODE(500,"账号已被注册"),
        REPEAT_USER_EMAIL(500,"邮箱已被注册");

        private Integer code;

        private String msg;

        ResultEnum(int code, String msg) {
            this.code=code;
            this.msg=msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
