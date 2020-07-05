package com.example.grocy.Models;

public class PhotosModel {
    String photoImage, photoCaption;

    public PhotosModel() {
    }

    public PhotosModel(String photoImage, String photoCaption) {
        this.photoImage = photoImage;
        this.photoCaption = photoCaption;
    }

    public String getPhotoImage() {
        return photoImage;
    }

    public String getPhotoCaption() {
        return photoCaption;
    }
}
