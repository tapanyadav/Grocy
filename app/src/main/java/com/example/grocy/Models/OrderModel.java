package com.example.grocy.Models;

public class OrderModel {
    public String Shop, Address, Item, Date,Amount, Delivery_status;
    public int Imageid;



    public OrderModel(String Shop, String Address, String Item, String Date, int Imageid, String Amount, String Delivery_status) {
        this.Shop = Shop;
        this.Address = Address;
        this.Item = Item;
        this.Date = Date;
        this.Imageid = Imageid;
        this.Amount = Amount;
        this.Delivery_status=Delivery_status;
    }

    public String getShop() {
        return Shop;
    }

    public void setShop(String Shop) {
        this.Shop = Shop;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String Item) {
        this.Item = Item;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public int getImageid() {
        return Imageid;
    }

    public void setImageid(int Imageid) {
        this.Imageid = Imageid;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getDelivery_status() {
        return Delivery_status;
    }

    public void setDelivery_status(String Delivery_status) {
        this.Delivery_status = Delivery_status;
    }





}
