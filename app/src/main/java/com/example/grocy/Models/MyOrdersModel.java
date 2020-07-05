package com.example.grocy.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MyOrdersModel implements Serializable {

    public String shopName, dateTime, deliveryStatus;
    public double orderAmount;
    public String shopImage;
    public boolean favOrder;
    public String orderId;
    public String userAddress;
    public String orderPaymentMode;
    ArrayList<HashMap<String, Object>> items = new ArrayList();

    public MyOrdersModel() {
    }

    public MyOrdersModel(String shopName, String dateTime, String deliveryStatus, double orderAmount, String shopImage, boolean favOrder, String orderId, String userAddress, String orderPaymentMode, ArrayList<HashMap<String, Object>> items) {
        this.shopName = shopName;
        this.dateTime = dateTime;
        this.deliveryStatus = deliveryStatus;
        this.orderAmount = orderAmount;
        this.shopImage = shopImage;
        this.favOrder = favOrder;
        this.orderId = orderId;
        this.userAddress = userAddress;
        this.orderPaymentMode = orderPaymentMode;
        this.items = items;
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
}
