package com.qzx.user.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security")
@RefreshScope
@Data
public class SecurityProperties {

    /**
     * 匿名访问
     */
    private String[] anonymous;

    /**
     * 不鉴权访问
     */
    private String[] permitAll;

}
