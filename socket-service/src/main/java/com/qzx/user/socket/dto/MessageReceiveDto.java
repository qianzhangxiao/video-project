package com.qzx.user.socket.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class MessageReceiveDto implements Serializable {

    private Integer type;

    private String sessionName;

    private String content;

    private Set<String> sessionNames;
}
