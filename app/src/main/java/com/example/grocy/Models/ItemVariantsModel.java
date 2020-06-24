package com.example.grocy.Models;

public class ItemVariantsModel {

    String itemPrice;
    String itemQuantity;

    public ItemVariantsModel(String itemPrice, String itemQuantity) {
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public ItemVariantsModel() {

    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
