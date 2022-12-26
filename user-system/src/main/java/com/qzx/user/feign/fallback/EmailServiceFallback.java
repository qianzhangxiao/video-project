package com.qzx.user.feign.fallback;

import com.qzx.user.feign.service.EmailService;
import com.qzx.user.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailServiceFallback implements FallbackFactory<EmailService> {
    @Override
    public EmailService create(Throwable cause) {
        log.error("异常信息--{}",cause.getMessage());
        return new EmailService() {
            @Override
            public ResponseResult<?> sendEmailCode(String emailCode, String to) {
               return ResponseResult.error(500,"验证码发送失败，请稍后重试");
            }
            @Override
            public ResponseResult<?> activityAccount(String userCode, String to) {
                return ResponseResult.error(500,"激活邮件发送失败，请稍后重试");
            }

            @Override
            public ResponseResult<?> addUser() {
                return ResponseResult.error(500,"新增用户失败");
            }
        };
    }
}
