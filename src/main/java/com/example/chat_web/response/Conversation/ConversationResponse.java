package com.example.chat_web.response.Conversation;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationResponse {
    private Long id;
    private String user1;
    private String user2;
    private String lastMessage;
    private String lastMessageTime;
}
