package com.example.grocy.Models;

public class CategoriesModel {

    private String catImage, catBackground;
    private String catType;

    CategoriesModel() {
    }

    CategoriesModel(String catImage, String catBackground, String catType) {
        this.catImage = catImage;
        this.catBackground = catBackground;
        this.catType = catType;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getCatBackground() {
        return catBackground;
    }

    public void setCatBackground(String catBackground) {
        this.catBackground = catBackground;
    }

    public String getCatType() {
        return catType;
    }

    public void setCatType(String catType) {
        this.catType = catType;
    }


}
