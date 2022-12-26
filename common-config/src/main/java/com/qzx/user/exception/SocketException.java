package com.qzx.user.exception;

/**
 * 自定义异常
 * */
public class SocketException extends RuntimeException {

    public SocketException(){
        super();
    }

    public SocketException(String msg){
        super(msg);
    }

    public SocketException(String msg, Throwable throwable){
        super(msg,throwable);
    }

}
