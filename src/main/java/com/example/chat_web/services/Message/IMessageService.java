package com.example.chat_web.services.Message;

import com.example.chat_web.entities.Message;
import com.example.chat_web.response.Message.MessageResponse;

import java.util.List;

public interface IMessageService {

    List<MessageResponse> getListMessageByUser(String user1, String user2);

    List<MessageResponse> getListConversationByUsername(String username);
}
