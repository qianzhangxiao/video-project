package com.qzx.user.seata.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;


/**
 * 读取自定义 yaml 文件工厂类
 */
public class YmalPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Properties properties = loadYamlProperties(resource);
        String sourceName =name!=null?name:resource.getResource().getFilename();
        return new PropertiesPropertySource(sourceName,properties);
    }

    private Properties loadYamlProperties(EncodedResource resource){
        YamlPropertiesFactoryBean factoryBean =new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource.getResource());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

}
