package com.example.yallabee3.model;

public class User {

    private String userId;
    private String fullName;
    private String email;
    private String phone;
    private String imgUrl;
    private String country;
    private String subCountry;
    private String area;

    public User() {
    }

    public User(String userId, String fullName, String email, String imgUrl, String country, String subCountry ,String area) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = imgUrl;
        this.country = country;
        this.subCountry = subCountry;
        this.area = area;
    }

    public User(String userId, String fullName, String email,String area) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.area = area;
    }

    public User(String userId, String fullName, String email, String phone, String imgUrl, String country, String subCountry,String area) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.country = country;
        this.subCountry = subCountry;
        this.area = area;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSubCountry() {
        return subCountry;
    }

    public void setSubCountry(String subCountry) {
        this.subCountry = subCountry;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
