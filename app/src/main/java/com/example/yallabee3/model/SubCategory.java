package com.example.yallabee3.model;

public class SubCategory {

    private String id;
    private String name;
    private String numberOfProducts;
    private String catId;

    public SubCategory() {
    }

    public SubCategory(String id, String name, String numberOfProducts, String catId) {
        this.id = id;
        this.name = name;
        this.numberOfProducts = numberOfProducts;
        this.catId = catId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(String numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
}
