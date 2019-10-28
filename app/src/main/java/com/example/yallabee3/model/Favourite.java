package com.example.yallabee3.model;

public class Favourite {
    private String rootId;
    private String productId;

    public Favourite(String productId , String userId) {
        this.productId = productId;
        this.userId = userId;
    }

    private String userId;

    public Favourite() {
    }

    public Favourite(String rootId, String productId, String userId) {
        this.rootId = rootId;
        this.productId = productId;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
