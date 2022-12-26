package com.qzx.user.configs;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @description:
 * @author: qc
 * @time: 2021/4/9 10:24
 */
@Configuration
public class DataSourceConfig {

    // 配置druid数据源，使yml配置文件的配置属性生效
    @ConfigurationProperties(prefix = "spring.datasource")
    @RefreshScope
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
}
