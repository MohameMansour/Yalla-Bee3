package com.example.yallabee3.model;

public class User {

    private String userId;
    private String fullName;
    private String email;
    private String phone;
    private String imgUrl;

    public User() {
    }

    public User(String userId, String fullName, String email, String imgUrl) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public User(String userId, String fullName, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }

    public User(String userId, String fullName, String email, String phone, String imgUrl) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.imgUrl = imgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
