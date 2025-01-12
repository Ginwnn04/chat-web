package com.example.chat_web.controllers;


import com.example.chat_web.entities.Message;
import com.example.chat_web.response.APIResponse;
import com.example.chat_web.services.Message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    @GetMapping("/messages")
    public ResponseEntity<APIResponse> getMessages(@RequestParam String user1, @RequestParam String user2) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Lay danh sach tin nhan thanh cong");
        apiResponse.setData(messageService.getListMessageByUser(user1, user2));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/messages/{username}/conversations")
    public ResponseEntity<APIResponse> getConversations(@PathVariable String username) {
        System.out.println("username: " + username);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Lay danh sach cuoc tro chuyen thanh cong");
        apiResponse.setData(messageService.getListConversationByUsername(username));
        return ResponseEntity.ok(apiResponse);
    }
}
