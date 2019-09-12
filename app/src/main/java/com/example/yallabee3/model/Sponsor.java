package com.example.yallabee3.model;

public class Sponsor {

    private String id;
    private String title;
    private String description;
    private String price;
    private String productImageUrl;
    private String phoneNumber;
    private String location;
    private String time;

    public Sponsor() {
    }

    public Sponsor(String id, String title, String description, String price, String productImageUrl, String phoneNumber, String location, String time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.productImageUrl = productImageUrl;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.time = time;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
