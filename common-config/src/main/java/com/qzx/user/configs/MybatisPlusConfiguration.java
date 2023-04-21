package com.qzx.user.configs;

import cn.hutool.core.net.NetUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfiguration {

    /**
     * 3.4.0 版本以上mybatis-plus插件配置，详细说明：https://baomidou.com/pages/2976a3/#mybatisplusinterceptor
     * */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();
        /**
         * 分页插件
         * */
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor());
        /**
         * 乐观锁插件
         */
        mybatisPlusInterceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());

        return mybatisPlusInterceptor;
    }

    /**
     * 分页插件
     */
    private PaginationInnerInterceptor paginationInnerInterceptor(){
        return new PaginationInnerInterceptor(DbType.MYSQL);
    }

    /**
     * 乐观锁插件
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 主键策略--绑定网卡实现雪花算法生成主键id
     */
    @Bean
    public IdentifierGenerator idGenerator(){
        return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
    }

}
