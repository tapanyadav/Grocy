package com.example.grocy.Models;

public class PhotosModel {
    String photoImage, photoCaption;
    String shopImage, shopName, shopAddress;

    public PhotosModel() {
    }

    public PhotosModel(String photoImage, String photoCaption, String shopImage, String shopName, String shopAddress) {
        this.photoImage = photoImage;
        this.photoCaption = photoCaption;
        this.shopImage = shopImage;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
    }

    public String getPhotoImage() {
        return photoImage;
    }

    public String getPhotoCaption() {
        return photoCaption;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
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

    public void setPhotoImage(String photoImage) {
        this.photoImage = photoImage;
    }

    public void setPhotoCaption(String photoCaption) {
        this.photoCaption = photoCaption;
    }
}
