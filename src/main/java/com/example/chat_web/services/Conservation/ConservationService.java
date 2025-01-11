package com.example.chat_web.services.Conservation;

import com.example.chat_web.entities.Conversation;
import com.example.chat_web.entities.Users;
import com.example.chat_web.repositories.Conversation.ConversationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConservationService implements IConservationService {

    private final ConversationRepository conversationRepository;
    @Override
    public Conversation isExistConversation(String user1, String user2) {
        return conversationRepository.isExistConversation(user1, user2).orElse(null);
    }

    @Override
    public Conversation createConversation(String user1, String user2) {
        Conversation conversation = conversationRepository.isExistConversation(user1, user2).orElse(null);
        if (conversation == null) {
            conversation = Conversation.builder()
                    .user1(Users.builder().username(user1).build())
                    .user2(Users.builder().username(user2).build())
                    .build();
            conversationRepository.save(conversation);
        }
        return conversation;
    }
}
