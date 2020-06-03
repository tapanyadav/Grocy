package com.example.grocy.Models;

public class CategoriesDetailsModel {

    private String shopImage, shopStatusBackground;
    private String shopName, shopAddress, shopOff, shopLimits, shopStatus, shopCategory, shopRating, shopType;

    CategoriesDetailsModel() {

    }

    CategoriesDetailsModel(String shopImage, String shopStatusBackground, String shopName, String shopAddress, String shopOff, String shopLimits, String shopStatus, String shopCategory, String shopRating, String shopType) {
        this.shopImage = shopImage;
        this.shopStatusBackground = shopStatusBackground;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopOff = shopOff;
        this.shopLimits = shopLimits;
        this.shopStatus = shopStatus;
        this.shopCategory = shopCategory;
        this.shopRating = shopRating;
        this.shopType = shopType;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopStatusBackground() {
        return shopStatusBackground;
    }

    public void setShopStatusBackground(String shopStatusBackground) {
        this.shopStatusBackground = shopStatusBackground;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopOff() {
        return shopOff;
    }

    public void setShopOff(String shopOff) {
        this.shopOff = shopOff;
    }

    public String getShopLimits() {
        return shopLimits;
    }

    public void setShopLimits(String shopLimits) {
        this.shopLimits = shopLimits;
    }

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopRating() {
        return shopRating;
    }

    public void setShopRating(String shopRating) {
        this.shopRating = shopRating;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
