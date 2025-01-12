package com.example.chat_web;

import java.sql.Date;

public class Message {
    private MessageType type;
    private String content;
    private String room;
    private String sender;
    private Date create_at;

    public Message() {
    }

    public Message(String sender, MessageType type, String content) {
        this.sender = sender;
        this.type = type;
        this.content = content;
    }


    public String getSender() {
        return sender;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public enum MessageType {
        SERVER, CLIENT
    }
}


