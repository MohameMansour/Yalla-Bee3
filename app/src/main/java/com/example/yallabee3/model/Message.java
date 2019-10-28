package com.example.yallabee3.model;

public class Message {

    private String id;
    private String message;
    private String sender;
    private String receiver;
    private boolean isSeen;
    private String productId;
    private String date;

    public Message() {
    }

    public Message(String id, String message, String sender, String receiver, boolean isSeen, String productId) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.isSeen = isSeen;
        this.productId = productId;
    }

    public Message(String id, String message, String sender, String receiver, boolean isSeen, String productId, String date) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.isSeen = isSeen;
        this.productId = productId;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDate() { return date;}

    public void setDate(String date) { this.date = date; }
}
