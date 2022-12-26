package com.qzx.user.socket.interceptor;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.Charset;
import java.util.Map;

@Component
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {

    /**
     * websocket握手前执行方法
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("握手前");
        //获取请求参数中的用户链接码（loginSession)
        final Map<String, String> requestMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), Charset.defaultCharset());
        if (ObjectUtils.isEmpty(requestMap)||ObjectUtils.isEmpty(requestMap.get("loginSession"))){
            log.error("用户登录已失效");
            return false;
        }
        //loginSession放入socket
        attributes.put("loginSession",requestMap.get("loginSession"));
        return true;
    }

    /**
     * websocket握手后执行方法
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("握手完成");
    }
}
