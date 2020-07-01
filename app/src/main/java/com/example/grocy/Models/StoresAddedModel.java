package com.example.grocy.Models;

public class StoresAddedModel {

    public String storeAddStatus, storeImage, storeLocation, storeName;

    public StoresAddedModel() {
    }

    public StoresAddedModel(String storeAddStatus, String storeImage, String storeLocation, String storeName) {
        this.storeAddStatus = storeAddStatus;
        this.storeImage = storeImage;
        this.storeLocation = storeLocation;
        this.storeName = storeName;
    }

    public String getStoreAddStatus() {
        return storeAddStatus;
    }

    public void setStoreAddStatus(String storeAddStatus) {
        this.storeAddStatus = storeAddStatus;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
