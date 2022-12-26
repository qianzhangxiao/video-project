package com.qzx.user.socket.utils;


import lombok.SneakyThrows;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ManageSocketSession {

    private static Map<String, WebSocketSession> SESSION_MAP;

    private ManageSocketSession(){

    }
    static {
        SESSION_MAP = new ConcurrentHashMap<>();
    }

    /**
     * 添加session
     * @param name
     * @param session
     */
    public static void putSession(String name,WebSocketSession session){
        SESSION_MAP.put(name,session);
    }

    /**
     * 删除session,会返回被删除的session
     * @param name
     */
    public static WebSocketSession removeSession(String name){
        return SESSION_MAP.remove(name);
    }

    /**
     * 获取session
     * @param name
     * @return
     */
    public static WebSocketSession getSession(String name){
        return SESSION_MAP.get(name);
    }

    /**
     * 删除并关闭session
     * @param name
     */
    @SneakyThrows
    public static void removeAndCloseSession(String name){
        WebSocketSession session = removeSession(name);
        if (session!=null&&session.isOpen()){
            session.close();
        }
    }

    /**
     * 给指定人发送消息
     * @param name
     * @param content
     */
    @SneakyThrows
    public static void sendToUser(String name,String content){
        final WebSocketSession session = getSession(name);
        if (session!=null&&session.isOpen()){
            synchronized (name){
                session.sendMessage(new TextMessage(content));
            }
        }
    }

    /**
     * 给组内人发消息
     * @param names
     * @param content
     */
    public static void sendToGroup(Set<String> names, String content){
        if (!ObjectUtils.isEmpty(names)){
            names.forEach(name->{
                WebSocketSession session = getSession(name);
                if (session!=null&&session.isOpen()){
                    synchronized (name){
                        try {
                            session.sendMessage(new TextMessage(content));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    /**
     * 给当前所有登录人发送消息
     * @param content
     */
    public static void sendToAll(String content){
        SESSION_MAP.forEach((name,session)->{
            if (session!=null&&session.isOpen()){
                try {
                    synchronized (name){
                        session.sendMessage(new TextMessage(content));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
