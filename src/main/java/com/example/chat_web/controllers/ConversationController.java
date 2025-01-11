package com.example.chat_web.controllers;

import com.example.chat_web.entities.Conversation;
import com.example.chat_web.response.APIResponse;
import com.example.chat_web.services.Conservation.ConservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConversationController {

    private final ConservationService conservationService;
    @GetMapping("/conversations")
    public ResponseEntity<APIResponse> isExistConversation(@RequestParam String user1, @RequestParam String user2) {
        Conversation conversation = conservationService.isExistConversation(user1, user2);
        APIResponse response = new APIResponse();
        response.setSuccess(true);
        response.setMessage("Lay cuoc tro chuyen thanh cong");
        response.setData(conversation);
        return ResponseEntity.ok(response);

    }
}
