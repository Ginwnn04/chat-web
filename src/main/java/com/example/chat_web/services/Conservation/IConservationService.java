package com.example.chat_web.services.Conservation;

import com.example.chat_web.entities.Conversation;

public interface IConservationService {
    Conversation isExistConversation(String user1, String user2);

    Conversation createConversation(String user1, String user2);
}
