package com.qzx.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@MapperScan(basePackages = {"com.qzx.user.mapper"})
@EnableDiscoveryClient
@EnableFeignClients //开启feignClient注解
@EnableTransactionManagement //开始事务
public class UserSystemApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(UserSystemApplication.class, args);
        System.out.println(run);
    }
}
