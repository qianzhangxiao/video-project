package com.qzx.user.video.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Data
@RefreshScope
@ConfigurationProperties(prefix = "attach")
public class SelfConfigProperties {

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 是否本地文件
     */
    private Boolean isLocal;
}
