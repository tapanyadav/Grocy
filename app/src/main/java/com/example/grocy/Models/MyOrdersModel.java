package com.example.grocy.Models;

public class MyOrdersModel {

    public String shopName, shopAddress, orderedItems, dateTime, deliveryStatus, orderAmount;
    public String shopImage;

    public MyOrdersModel() {
    }

    public MyOrdersModel(String shopName, String shopAddress, String orderedItems, String dateTime, String deliveryStatus, String orderAmount, String shopImage) {
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

    public String getShopAddress() {
        return shopAddress;
    }

    public String getOrderedItems() {
        return orderedItems;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public String getShopImage() {
        return shopImage;
    }
}
