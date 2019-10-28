package com.example.yallabee3.model;

public class Product {
    private String id;
    private String title;
    private String description;
    private String price;
    private String phone;
    private String country;
    private String productImageUrl;
    private String userId;
    private String catId;
    private String subCatId;
    private String date;
    private String productImageUrl2;
    private String productImageUrl3;
    private String productImageUrl4;
    private String subcountry;

    public Product(String id, String title, String description, String price, String phone, String country, String productImageUrl, String userId, String catId, String subCatId, String date, String productImageUrl2, String productImageUrl3, String productImageUrl4,String subcountry) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.country = country;
        this.productImageUrl = productImageUrl;
        this.userId = userId;
        this.catId = catId;
        this.subCatId = subCatId;
        this.productImageUrl2 = productImageUrl2;
        this.productImageUrl3 = productImageUrl3;
        this.productImageUrl4 = productImageUrl4;
        this.subcountry=subcountry;
    }

    public Product(String id, String title, String description, String price, String productImageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.productImageUrl = productImageUrl;
    }

    public Product(String id, String title, String description, String price, String phone, String country, String productImageUrl, String userId, String catId, String subCatId, String date,String subcountry) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.country = country;
        this.productImageUrl = productImageUrl;
        this.userId = userId;
        this.catId = catId;
        this.subCatId = subCatId;
        this.date = date;
        this.subcountry=subcountry;
    }

    public Product(String id, String title, String description, String price, String phone, String country, String productImageUrl, String userId,String subcountry) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.country = country;
        this.productImageUrl = productImageUrl;
        this.userId = userId;
        this.subcountry=subcountry;
    }

    public Product() {
    }

    public Product(String id, String title, String productImageUrl) {
        this.id = id;
        this.title = title;
        this.productImageUrl = productImageUrl;
    }

    public Product(String id, String title, String description, String price, String phone, String country, String productImageUrl,String subcountry) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.country = country;
        this.productImageUrl = productImageUrl;
        this.subcountry=subcountry;

    }

    public Product(String id, String title, String description, String price, String country, String productImageUrl,String subcountry) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.country = country;
        this.productImageUrl = productImageUrl;
        this.subcountry=subcountry;
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
        return country;
    }

    public void setPlace(String place) {
        this.country = place;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getProductImageUrl2() {
        return productImageUrl2;
    }

    public void setProductImageUrl2(String productImageUrl2) {
        this.productImageUrl2 = productImageUrl2;
    }

    public String getProductImageUrl3() {
        return productImageUrl3;
    }

    public void setProductImageUrl3(String productImageUrl3) {
        this.productImageUrl3 = productImageUrl3;
    }

    public String getProductImageUrl4() {
        return productImageUrl4;
    }

    public void setProductImageUrl4(String productImageUrl4) {
        this.productImageUrl4 = productImageUrl4;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubcountry() {
        return subcountry;
    }

    public void setSubcountry(String subcountry) {
        this.subcountry = subcountry;
    }
}
