package com.qzx.user.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * feign文件上传
 * 1、增加以下配置
 * 2、请求头加上consumes = MediaType.MULTIPART_FORM_DATA_VALUE
 *      例如：@GetMapping(value = "/email/sendEmailWithFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 * 3、请求@RequestPart封装请求参数
 *      例如：@RequestPart("file")MultipartFile[]files
 */
@Configuration
public class MultipartSupportConfig {

    @Resource
    private ObjectFactory<HttpMessageConverters> messageConvertersObjectFactory;

    @Bean
    public Encoder feignFormEncoder(){
        return new SpringFormEncoder(new SpringEncoder(messageConvertersObjectFactory));
    }

}
