package com.example.chat_web.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    private boolean success;
    private String message;
    private Object data;

}
