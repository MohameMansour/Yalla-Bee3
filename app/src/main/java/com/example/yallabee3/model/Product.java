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
    private String subCatId;
    private String productImageUrl2;
    private String productImageUrl3;
    private String productImageUrl4;

    public Product(String id, String title, String description, String price, String phone, String place, String productImageUrl, String userId, String catId, String subCatId, String productImageUrl2, String productImageUrl3, String productImageUrl4) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.place = place;
        this.productImageUrl = productImageUrl;
        this.userId = userId;
        this.catId = catId;
        this.subCatId = subCatId;
        this.productImageUrl2 = productImageUrl2;
        this.productImageUrl3 = productImageUrl3;
        this.productImageUrl4 = productImageUrl4;
    }

    public Product(String id, String title, String description, String price, String productImageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.productImageUrl = productImageUrl;
    }

    public Product(String id, String title, String description, String price, String phone, String place, String productImageUrl, String userId, String catId, String subCatId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.place = place;
        this.productImageUrl = productImageUrl;
        this.userId = userId;
        this.catId = catId;
        this.subCatId = subCatId;
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
}
