package com.example.grocy.Models;

public class CategoriesModel {
    private int image_drawable;
    private String text_cat;

    public String getText_cat() {
        return text_cat;
    }

    public void setText_cat(String text_cat) {
        this.text_cat = text_cat;
    }

    public int getImage_drawable() {
        return image_drawable;
    }

    public void setImage_drawable(int image_drawable) {
        this.image_drawable = image_drawable;

    }
}
