package com.qzx.user.exception;

import com.qzx.user.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;


@RestControllerAdvice
@Slf4j
public class CommonExceptionHandle {


    @ExceptionHandler(Exception.class)
    public ResponseResult<?> handleMultipartException(Exception e){
        printException(e);
        if (e instanceof MultipartException){
            //单个文件大小超出限制抛出的异常
            return (e.getCause().getCause() instanceof FileSizeLimitExceededException ||e.getCause().getCause() instanceof SizeLimitExceededException)?ResponseResult.error(ResponseResult.ResultEnum.FILE_OVERSIZE): ResponseResult.error(ResponseResult.ResultEnum.FILE_ERROR);
        }
        if (e instanceof NullPointerException){
            return ResponseResult.error(ResponseResult.ResultEnum.NUll_ERROR);
        }
        if (e instanceof BusinessException){
            return ResponseResult.error(500,e.getMessage());
        }
       return ResponseResult.error(500,"未知异常，请联系管理员");
    }

    private void printException(Exception e){
        log.error("message异常信息为=========》"+e.getMessage());
        e.printStackTrace();
    }


}
