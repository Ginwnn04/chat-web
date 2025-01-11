package com.example.chat_web.services.Conservation;

import com.example.chat_web.entities.Conversation;
import com.example.chat_web.entities.Users;
import com.example.chat_web.exceptions.DataNotFoundException;
import com.example.chat_web.repositories.Conversation.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConservationService implements IConservationService {

    private final ConversationRepository conversationRepository;
    @Override
    public Conversation isExistConversation(String user1, String user2) {
        return conversationRepository.isExistConversation(user1, user2)
                .orElseThrow(() -> new DataNotFoundException("Khong ton tai"));
    }

    @Override
    public Conversation createConversation(String user1, String user2) {
        Conversation conversation = conversationRepository.isExistConversation(user1, user2).orElse(null);
        if (conversation == null) {
            conversation = Conversation.builder()
                    .user1(Users.builder().username(user1).build())
                    .user2(Users.builder().username(user2).build())
                    .build();
        }
        return conversation;
    }
}
