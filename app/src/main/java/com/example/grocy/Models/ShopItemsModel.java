package com.example.grocy.Models;

public class ShopItemsModel {
    private String itemsImage;
    private String itemsPrice;
    private String itemsProductDescription;
    private String itemsProductName;
    private String itemsQuantity;
    private String itemID;
    private String shopID;

    public ShopItemsModel() {

    }

    public ShopItemsModel(String itemsImage, String itemsPrice, String itemsProductDescription, String itemsProductName, String itemsQuantity) {
        this.itemsImage = itemsImage;
        this.itemsPrice = itemsPrice;
        this.itemsProductDescription = itemsProductDescription;
        this.itemsProductName = itemsProductName;
        this.itemsQuantity = itemsQuantity;
    }

    public String getItemsImage() {
        return itemsImage;
    }

    public void setItemsImage(String itemsImage) {
        this.itemsImage = itemsImage;
    }

    public String getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(String itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public String getItemsProductDescription() {
        return itemsProductDescription;
    }

    public void setItemsProductDescription(String itemsProductDescription) {
        this.itemsProductDescription = itemsProductDescription;
    }

    public String getItemsProductName() {
        return itemsProductName;
    }

    public void setItemsProductName(String itemsProductName) {
        this.itemsProductName = itemsProductName;
    }

    public String getItemsQuantity() {
        return itemsQuantity;
    }

    public void setItemsQuantity(String itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }
}
