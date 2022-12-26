package com.qzx.user;

import com.qzx.user.utils.CommonEnum;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class UserSystemApplicationTests {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    void contextLoads() {
        redissonClient.getBucket("hello").set("bug");
        String test = (String) redissonClient.getBucket("hello").get();
        System.out.println(test);
    }

    @Test
    void testEncoder() {
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

    @Test
    void testEnum(){
        System.out.println(CommonEnum.UserStatusEnum.getCodeByMsg("未激活"));
    }

}
