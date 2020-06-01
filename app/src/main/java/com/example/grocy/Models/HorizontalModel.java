package com.example.grocy.Models;

public class HorizontalModel {

    private String shopHorizontalBackgroundImage, shopHorizontalImage;
    private String shopHorizontalName;

    public HorizontalModel() {

    }

    public HorizontalModel(String shopHorizontalBackgroundImage, String shopHorizontalImage, String shopHorizontalName) {
        this.shopHorizontalBackgroundImage = shopHorizontalBackgroundImage;
        this.shopHorizontalImage = shopHorizontalImage;
        this.shopHorizontalName = shopHorizontalName;
    }

    public String getShopHorizontalBackgroundImage() {
        return shopHorizontalBackgroundImage;
    }

    public void setShopHorizontalBackgroundImage(String shopHorizontalBackgroundImage) {
        this.shopHorizontalBackgroundImage = shopHorizontalBackgroundImage;
    }

    public String getShopHorizontalImage() {
        return shopHorizontalImage;
    }

    public void setShopHorizontalImage(String shopHorizontalImage) {
        this.shopHorizontalImage = shopHorizontalImage;
    }

    public String getShopHorizontalName() {
        return shopHorizontalName;
    }

    public void setShopHorizontalName(String shopHorizontalName) {
        this.shopHorizontalName = shopHorizontalName;
    }

}
