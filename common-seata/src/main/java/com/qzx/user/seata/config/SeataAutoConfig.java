package com.qzx.user.seata.config;

import com.qzx.user.seata.utils.YmalPropertySourceFactory;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:seata-config.yml",factory = YmalPropertySourceFactory.class)
@EnableAutoDataSourceProxy(useJdkProxy = true)
@Configuration(proxyBeanMethods = false)
public class SeataAutoConfig  {
}
