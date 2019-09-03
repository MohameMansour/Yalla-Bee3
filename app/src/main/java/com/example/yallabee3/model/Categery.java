package com.example.yallabee3.model;

public class Categery {

    private String id;
    private String name;
    private String image;
    private String numberOfProduct;

    public Categery() {
    }

    public Categery(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Categery(String id, String name, String image, String numberOfProduct) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.numberOfProduct = numberOfProduct;
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

    public String getImage() {
        return image;
    }

    public void setImage(String productImageUrl) {
        this.image = productImageUrl;
    }

    public String getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(String numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

}
