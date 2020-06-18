package com.example.grocy.Models;

public class VisionModel {

    private int imageVision;
    private String textVisionHead, textVisionData;

    public VisionModel(int imageVision, String textVisionHead, String textVisionData) {
        this.imageVision = imageVision;
        this.textVisionHead = textVisionHead;
        this.textVisionData = textVisionData;
    }

    public int getImageVision() {
        return imageVision;
    }

    public void setImageVision(int imageVision) {
        this.imageVision = imageVision;
    }

    public String getTextVisionHead() {
        return textVisionHead;
    }

    public void setTextVisionHead(String textVisionHead) {
        this.textVisionHead = textVisionHead;
    }

    public String getTextVisionData() {
        return textVisionData;
    }

    public void setTextVisionData(String textVisionData) {
        this.textVisionData = textVisionData;
    }
}
