package com.example.grocy.Models;

public class ShopsModel {
    private int image_shop_drawable;
    private String text_shop_name, text_shop_type, text_shop_limit, text_shop_off, text_shop_rating;

    public String getText_shop_name() {
        return text_shop_name;
    }

    public void setText_shop_name(String text_shop_name) {
        this.text_shop_name = text_shop_name;
    }

    public String getText_shop_type() {
        return text_shop_type;
    }

    public void setText_shop_type(String text_shop_type) {
        this.text_shop_type = text_shop_type;
    }

    public String getText_shop_limit() {
        return text_shop_limit;
    }

    public void setText_shop_limit(String text_shop_limit) {
        this.text_shop_limit = text_shop_limit;
    }

    public String getText_shop_off() {
        return text_shop_off;
    }

    public void setText_shop_off(String text_shop_off) {
        this.text_shop_off = text_shop_off;
    }

    public String getText_shop_rating() {
        return text_shop_rating;
    }

    public void setText_shop_rating(String text_shop_rating) {
        this.text_shop_rating = text_shop_rating;
    }


    public int getImage_shop_drawable() {

        return image_shop_drawable;
    }

    public void setImage_shop_drawable(int image_shop_drawable) {
        this.image_shop_drawable = image_shop_drawable;

    }
}
