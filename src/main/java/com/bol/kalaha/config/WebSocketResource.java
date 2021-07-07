package com.bol.kalaha.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketResource {
    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketResource(SimpMessagingTemplate template) {
        this.template = template;
    }


    public void publishWebSocket(String data) {
        template.convertAndSend("/update", data);
    }
    public void publishWebSocket(String data, Long gameId) {
        template.convertAndSend("/update/"+gameId, data);
    }


}