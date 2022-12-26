package com.qzx.user.socket.handle;

import com.alibaba.fastjson2.JSON;
import com.qzx.user.exception.BusinessException;
import com.qzx.user.exception.SocketException;
import com.qzx.user.socket.dto.MessageReceiveDto;
import com.qzx.user.socket.enums.MessageReceiveEnum;
import com.qzx.user.socket.utils.ManageSocketSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * websocket处理类
 */
@Component
@Slf4j
public class WebSocketHandle implements WebSocketHandler {


    /**
     * 用户连接上WebSocket的回调
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        final String loginSession = session.getAttributes().get("loginSession").toString();
        log.info("用户{}上线",loginSession);
        /*保存登录信息*/
        if (!ObjectUtils.isEmpty(loginSession)){
            ManageSocketSession.putSession(loginSession,session);
        }else{
            throw new SocketException("登录信息已失效");
        }
    }

    /**
     * 收到消息的回调
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        /*确认是否登录*/
        if (ObjectUtils.isEmpty(session.getAttributes().get("loginSession"))){
            log.error("用户登录已失效");
            throw new SocketException("登录已失效，请重新连接");
        }else{
            String msg = message.getPayload().toString();
            handleMessage(msg);
        }
    }

    /**
     *出现错误的回调
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("socket获取数据异常->{}",exception.getMessage());
        exception.printStackTrace();
    }

    /**
     *连接关闭的回调
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if (!ObjectUtils.isEmpty(session.getAttributes().get("loginSession"))){
            log.info("{}已下线",session.getAttributes().get("loginSession").toString());
            ManageSocketSession.removeAndCloseSession(session.getAttributes().get("loginSession").toString());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 消息处理
     * @param msg 接收到的消息
     */
    private void handleMessage(String msg){
        MessageReceiveDto receive = JSON.parseObject(msg,MessageReceiveDto.class);
        if (Objects.equals(MessageReceiveEnum.TO_ONE.getType(), receive.getType())&&!ObjectUtils.isEmpty(receive.getSessionName())){
            ManageSocketSession.sendToUser(receive.getSessionName(),receive.getContent());
        }
        if (Objects.equals(MessageReceiveEnum.TO_GROUP.getType(), receive.getType())&&!ObjectUtils.isEmpty(receive.getSessionNames())){
            ManageSocketSession.sendToGroup(receive.getSessionNames(), receive.getContent());
        }
        if (Objects.equals(MessageReceiveEnum.TO_ALL.getType(), receive.getType())){
            ManageSocketSession.sendToAll(receive.getContent());
        }
    }
}
