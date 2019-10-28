package com.example.yallabee3.model;

public class Categery {

    private String id;
    private String name;
    private String image;
//    private String numberOfProducts;

    public Categery() {
    }

    public Categery(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
//
//    public Categery(String id, String name, String image, String numberOfProducts) {
//        this.id = id;
//        this.name = name;
//        this.image = image;
//        this.numberOfProducts = numberOfProducts;
//    }

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

//    public String getNumberOfProducts() {
//        return numberOfProducts;
//    }
//
//    public void setNumberOfProducts(String numberOfProducts) {
//        this.numberOfProducts = numberOfProducts;
//    }

}
