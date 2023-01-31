package com.qzx.user.feign.service;

import com.qzx.user.dto.EmailInfo;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.feign.config.LoadBalanceConfig;
import com.qzx.user.feign.fallback.EmailServiceFallback;
import com.qzx.user.utils.ResponseResult;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/*声明需要调用的服务--服务降级可以打印异常信息*/
@FeignClient(value = "email-service",fallbackFactory = EmailServiceFallback.class)
//单配置，2020.3版本配置ribbon均衡策略方法,注意这里的name属性 需要和eureka服务提供者名字一致(provider配置的应用名称)，configuration对应负载均衡配置类
@LoadBalancerClient(name = "email-service",configuration = LoadBalanceConfig.class)
public interface EmailService {

    @GetMapping("/email/sendEmailCode")
    ResponseResult<?> sendEmailCode(@RequestParam("emailCode")String emailCode,@RequestParam("to") String to);

    @GetMapping("/email/activityAccount")
    ResponseResult<?> activityAccount(@RequestParam("userCode")String userCode,@RequestParam("to") String to);

    @GetMapping("/email/addUser")
    ResponseResult<?> addUser();

    @GetMapping(value = "/email/sendEmailWithFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult<?> sendEmailWithFile(@RequestPart("file")MultipartFile[]files, @RequestPart("emailInfo") EmailInfo emailInfo);

}
