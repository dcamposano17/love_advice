package com.example.dioncamposano17.loveadvice20.model;

/**
 * Created by dioncamposano17 on 22/02/2017.
 */

public class Chat {

    private String id, message, message_type;

    public Chat(String id, String message, String message_type) {
        this.id = id;
        this.message = message;
        this.message_type = message_type;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return message_type;
    }
}
