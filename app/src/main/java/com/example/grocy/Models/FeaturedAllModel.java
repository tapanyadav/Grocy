package com.example.grocy.Models;

public class FeaturedAllModel {
    private String shopStatus,shopNameAll,shopAddressAll,shopCategory,shopOff;
    private long shopRating;
    private String shopImage;



    public FeaturedAllModel() {
    }

    public FeaturedAllModel(String shopStatus, String shopNameAll, String shopAddressAll, String shopCategory, String shopOff, String shopImage, long shopRating) {
        this.shopStatus = shopStatus;
        this.shopNameAll = shopNameAll;
        this.shopAddressAll = shopAddressAll;
        this.shopCategory = shopCategory;
        this.shopOff = shopOff;
        this.shopImage=shopImage;
        this.shopRating = shopRating;
    }

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getShopNameAll() {
        return shopNameAll;
    }

    public void setShopNameAll(String shopNameAll) {
        this.shopNameAll = shopNameAll;
    }

    public String getShopAddressAll() {
        return shopAddressAll;
    }

    public void setShopAddressAll(String shopAddressAll) {
        this.shopAddressAll = shopAddressAll;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopOff() {
        return shopOff;
    }

    public void setShopOff(String shopOff) {
        this.shopOff = shopOff;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public long getShopRating() {
        return shopRating;
    }

    public void setShopRating(long shopRating) {
        this.shopRating = shopRating;
    }


}
