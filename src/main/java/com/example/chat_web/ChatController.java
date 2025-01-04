package com.example.chat_web;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    SimpMess
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        return message; // Trả lại tin nhắn để gửi đến client
    }

}
