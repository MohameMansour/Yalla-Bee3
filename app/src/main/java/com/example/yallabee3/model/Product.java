package com.example.yallabee3.model;

public class Product {
    private String id;
    private String title;
    private String description;
    private String price;
    private String phone;
    private String place;
    private String productImageUrl;
    private String userId;
    private String catId;


    public Product(String id, String title, String description, String price, String productImageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.productImageUrl = productImageUrl;
    }

    public Product(String id, String title, String description, String price, String phone, String place, String productImageUrl, String userId, String catId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.place = place;
        this.productImageUrl = productImageUrl;
        this.userId = userId;
        this.catId = catId;
    }

    public Product(String id, String title, String description, String price, String phone, String place, String productImageUrl, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.place = place;
        this.productImageUrl = productImageUrl;
        this.userId = userId;
    }

    public Product() {
    }

    public Product(String id, String title, String productImageUrl) {
        this.id = id;
        this.title = title;
        this.productImageUrl = productImageUrl;
    }

    public Product(String id, String title, String description, String price, String phone, String place, String productImageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.place = place;
        this.productImageUrl = productImageUrl;

    }

    public Product(String id, String title, String description, String price, String place, String productImageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.place = place;
        this.productImageUrl = productImageUrl;
    }

    //    public Product(String id, String title, String description, String price,String email, String productImageUrl ) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.price = price;
//        this.email=email;
//        this.productImageUrl = productImageUrl;
//
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
