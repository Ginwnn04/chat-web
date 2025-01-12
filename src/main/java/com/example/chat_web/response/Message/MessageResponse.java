package com.example.chat_web.response.Message;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {
    private Long id;
    private String sender;
    private String content;
    private String receiver;
    private Long conversationId;
}
