package com.example.chat_web.services.Message;

import com.example.chat_web.entities.Conversation;
import com.example.chat_web.entities.Message;
import com.example.chat_web.exceptions.DataNotFoundException;
import com.example.chat_web.repositories.Conversation.ConversationRepository;
import com.example.chat_web.repositories.Message.MessageRepository;
import com.example.chat_web.response.Message.MessageResponse;
import com.example.chat_web.services.Conservation.ConservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService{

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    @Override
    public List<MessageResponse> getListMessageByUser(String user1, String user2) {
        conversationRepository.isExistConversation(user1, user2)
                .orElseThrow(() -> new DataNotFoundException("Khong ton tai"));

        List<MessageResponse> listMesResponse = new ArrayList<>();
        messageRepository.getMessageByUsername(user1, user2).forEach(message -> {
            listMesResponse.add(MessageResponse.builder()
                    .id(message.getId())
                    .content(message.getContent())
                    .sender(message.getSender().getUsername())
                    .build());
        });
        return listMesResponse;
    }

    @Override
    public List<MessageResponse> getListConversationByUsername(String username) {
        List<Conversation> list = conversationRepository.findListConversation(username);
        List<Long> listId = new ArrayList<>();
        if (list.size() != 0) {
            list.forEach(conversation -> {
                listId.add(conversation.getId());
            });
        }
        List<MessageResponse> listMessageResponse = new ArrayList<>();
        List<Message> a = messageRepository.findByConversationId(listId);
        messageRepository.findByConversationId(listId).stream().forEach(message -> {
            listMessageResponse.add(MessageResponse.builder()
                    .id(message.getId())
                    .content(message.getContent())
                    .sender(message.getSender().getUsername())
                    .receiver(message.getConversation().getUser1().getUsername().equals(username) ?
                                    message.getConversation().getUser2().getUsername() :
                                    message.getConversation().getUser1().getUsername())
                    .conversationId(message.getConversation().getId())
                    .build());
        });


        return listMessageResponse;
    }
}
