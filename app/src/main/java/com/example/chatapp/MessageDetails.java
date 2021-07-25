package com.example.chatapp;

public class MessageDetails {
    String sender;
    String receiver;
    String message;

    public MessageDetails(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public MessageDetails() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender_msg(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
