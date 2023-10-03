package com.example.guardianhelmet;

import java.util.Date;

public class Message {
    private String text;
    private Date timestamp;

    public Message() {
        // Default constructor required for Firestore
    }

    public Message(String text, Date timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

