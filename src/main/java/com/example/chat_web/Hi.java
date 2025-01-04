package com.example.chat_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class Hi {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @PostMapping("/private-message")
    public void hi(@RequestBody Message message) {
        messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/private", message);
    }
}
