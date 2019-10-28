package com.example.yallabee3.model;

public class Chats {
    private String rootId;
    private String productId;
    private String senderId;
    private String receiverId;
    private String date;
    private String time;

    public Chats() {
    }

    public Chats(String rootId, String productId, String senderId, String receiverId,String date,String time) {
        this.rootId = rootId;
        this.productId = productId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = date;
        this.time = time;

    }

    public Chats(String rootId, String date, String time) {
        this.rootId = rootId;
        this.date = date;
        this.time = time;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
