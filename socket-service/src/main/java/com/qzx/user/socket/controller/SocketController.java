package com.qzx.user.socket.controller;

import com.qzx.user.socket.dto.MessageReceiveDto;
import com.qzx.user.socket.utils.ManageSocketSession;
import com.qzx.user.utils.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socket")
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class SocketController {

    @PostMapping("sendToOne")
    public ResponseResult<?> sendToOne(@RequestBody MessageReceiveDto receive){
        ManageSocketSession.sendToUser(receive.getSessionName(),receive.getContent());
        return ResponseResult.success();
    }

    @PostMapping("sendToGroup")
    public ResponseResult<?> sendToGroup(@RequestBody MessageReceiveDto receive){
        ManageSocketSession.sendToGroup(receive.getSessionNames(),receive.getContent());
        return ResponseResult.success();
    }

    @PostMapping("sendToAll")
    public ResponseResult<?> sendToAll(@RequestBody MessageReceiveDto receive){
        ManageSocketSession.sendToAll(receive.getContent());
        return ResponseResult.success();
    }

}
