package com.example.chat_web;

public class Message {
    private MessageType type;
    private String message;
    private String room;
    private String sender;

    public Message() {
    }

    public Message(String sender, MessageType type, String message) {
        this.sender = sender;
        this.type = type;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public enum MessageType {
        SERVER, CLIENT
    }
}


