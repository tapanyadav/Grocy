package com.example.grocy.Models;

import java.io.Serializable;

public class SearchShopModel implements Serializable {

    private String shopName, shopAddress, shopOff, shopLimits, shopStatus, shopCategory, shopType;
    private double shopRating;

    private String shopId;


    public SearchShopModel() {
    }

    public SearchShopModel(String shopId, String shopName, String shopAddress, String shopOff, String shopLimits, String shopStatus, String shopCategory, String shopType, double shopRating) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopOff = shopOff;
        this.shopLimits = shopLimits;
        this.shopStatus = shopStatus;
        this.shopCategory = shopCategory;
        this.shopType = shopType;
        this.shopRating = shopRating;
        this.shopId = shopId;
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

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public double getShopRating() {
        return shopRating;
    }

    public void setShopRating(double shopRating) {
        this.shopRating = shopRating;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
