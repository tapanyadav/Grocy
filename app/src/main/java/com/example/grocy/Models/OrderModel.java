package com.example.grocy.Models;

public class OrderModel {
    public String shopName, shopAddress, orderedItems, dateTime, deliveryStatus, orderAmount;
    public int shopImage;

    public OrderModel(String shopName, String shopAddress, String orderedItems, String dateTime, String deliveryStatus, String orderAmount, int shopImage) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.orderedItems = orderedItems;
        this.dateTime = dateTime;
        this.deliveryStatus = deliveryStatus;
        this.orderAmount = orderAmount;
        this.shopImage = shopImage;
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

    public String getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(String orderedItems) {
        this.orderedItems = orderedItems;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getShopImage() {
        return shopImage;
    }

    public void setShopImage(int shopImage) {
        this.shopImage = shopImage;
    }
}
