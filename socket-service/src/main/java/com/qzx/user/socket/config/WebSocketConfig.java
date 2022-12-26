package com.qzx.user.socket.config;

import com.qzx.user.socket.handle.WebSocketHandle;
import com.qzx.user.socket.interceptor.WebSocketInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 配置websocket服务端
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandle webSocketHandle;

    private final WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                // 配置处理类和访问路径
                .addHandler(webSocketHandle,"/chat")
                // 配置拦截器
                .addInterceptors(webSocketInterceptor)
                // 设置跨域
                .setAllowedOrigins("*");
    }
}
