package com.example.grocy.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
public class MyOrdersModel implements Serializable {

    public String shopName, deliveryStatus;
    public double orderAmount, taxAmount, itemAmount;
    public String shopAddress;
    public String shopImage;
    public boolean favOrder;
    public boolean bookmarkStatus;
    public String orderId;
    public String userAddress;
    public String orderPaymentMode;
    public String dateTime;
    ArrayList<HashMap<String, Object>> items = new ArrayList<>();


    public MyOrdersModel() {
    }


    public MyOrdersModel(String shopName, String deliveryStatus, double orderAmount, double taxAmount, double itemAmount, String shopAddress, String shopImage, boolean favOrder, String orderId, String userAddress, String orderPaymentMode, ArrayList<HashMap<String, Object>> items, String dateTime, boolean bookmarkStatus) {
        this.shopName = shopName;
        this.deliveryStatus = deliveryStatus;
        this.orderAmount = orderAmount;
        this.taxAmount = taxAmount;
        this.itemAmount = itemAmount;
        this.shopAddress = shopAddress;
        this.shopImage = shopImage;
        this.favOrder = favOrder;
        this.orderId = orderId;
        this.userAddress = userAddress;
        this.orderPaymentMode = orderPaymentMode;
        this.items = items;
        this.dateTime = dateTime;
        this.bookmarkStatus = bookmarkStatus;
    }

    public String getShopName() {
        return shopName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public String getShopImage() {
        return shopImage;
    }

    public boolean isFavOrder() {
        return favOrder;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getOrderPaymentMode() {
        return orderPaymentMode;
    }

    public ArrayList<HashMap<String, Object>> getItems() {
        return items;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public boolean isBookmarkStatus() {
        return bookmarkStatus;
    }
}
