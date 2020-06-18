package com.example.grocy.Models;

import java.util.ArrayList;

public class ShopItemsCategoryModel {
    private String item_category_name;
    private ArrayList<ShopItemsModel> shop_items_list;

    public ShopItemsCategoryModel() {
    }

    public ShopItemsCategoryModel(String item_category_name, ArrayList<ShopItemsModel> shop_items_list) {
        this.item_category_name = item_category_name;
        this.shop_items_list = shop_items_list;
    }

    public String getItem_category_name() {
        return item_category_name;
    }

    public void setItem_category_name(String item_category_name) {
        this.item_category_name = item_category_name;
    }

    public ArrayList<ShopItemsModel> getShop_items_list() {
        return shop_items_list;
    }

    public void setShop_items_list(ArrayList<ShopItemsModel> shop_items_list) {
        this.shop_items_list = shop_items_list;
    }
}
