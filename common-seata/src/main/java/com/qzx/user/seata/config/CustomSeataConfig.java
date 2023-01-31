package com.qzx.user.seata.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义seata拦截器，绑定xid
 */
@Configuration
public class CustomSeataConfig {

    @Bean
    public FilterRegistrationBean<SeataFilter> i18nFilterRegistrationBean() {
        FilterRegistrationBean<SeataFilter> registrationBean = new FilterRegistrationBean();
        SeataFilter seataFilter = new SeataFilter();

        registrationBean.setFilter(seataFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(-101); // 设置拦截顺序
        return registrationBean;
    }

}
