package com.example.chat_web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return message;

    }

    @MessageMapping("/sendMessagePrivate")
    @SendToUser("/queue/private")
    public Message sendMessagePrivate(@Payload Message message) {
//        messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/private", message);
        return message;

    }

//    @MessageMapping("/addUser")
//    public Message addUser(@Payload Message message,
//                               SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        System.out.println("addUser: " + message.getSender());
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        System.out.println(headerAccessor.getUser());
//        return message;
//    }

}
