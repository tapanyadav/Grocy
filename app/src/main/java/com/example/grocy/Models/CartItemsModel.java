package com.example.grocy.Models;

import java.io.Serializable;

public class CartItemsModel implements Serializable {
    private String itemsImage;
    private String itemsPrice;
    private String itemsQuantity;
    private String itemsName;
    private int itemCount;
    private String itemID;
    private String variantID = null;

    public CartItemsModel() {
    }

    public CartItemsModel(String itemsImage, String itemsPrice, String itemsQuantity, String itemsName, int itemCount) {
        this.itemsImage = itemsImage;
        this.itemsPrice = itemsPrice;
        this.itemsQuantity = itemsQuantity;
        this.itemsName = itemsName;
        this.itemCount = itemCount;
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

    public String getItemsQuantity() {
        return itemsQuantity;
    }

    public void setItemsQuantity(String itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getVariantID() {
        return variantID;
    }

    public void setVariantID(String variantID) {
        this.variantID = variantID;
    }
}
