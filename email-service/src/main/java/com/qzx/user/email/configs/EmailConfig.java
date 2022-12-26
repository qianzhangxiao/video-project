package com.qzx.user.email.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class EmailConfig {

    private final EmailProperties properties;

    @Bean(value = "myJavaMailSender")
    @RefreshScope
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(properties.getHost());
        javaMailSender.setUsername(properties.getUsername());
        javaMailSender.setPassword(properties.getPassword());
        javaMailSender.setDefaultEncoding(properties.getDefaultEncoding());
        return javaMailSender;
    }

}
