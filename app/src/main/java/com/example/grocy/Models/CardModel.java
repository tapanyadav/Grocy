package com.example.grocy.Models;

public class CardModel {

    private int imageCardBack, imageCardLogo;
    private String firstFour, fourthFour, holderName, holderExpireDate;

    public CardModel() {
    }

    public CardModel(int imageCardBack, int imageCardLogo, String firstFour, String fourthFour, String holderName, String holderExpireDate) {
        this.imageCardBack = imageCardBack;
        this.imageCardLogo = imageCardLogo;
        this.firstFour = firstFour;
        this.fourthFour = fourthFour;
        this.holderName = holderName;
        this.holderExpireDate = holderExpireDate;
    }

    public int getImageCardBack() {
        return imageCardBack;
    }

    public void setImageCardBack(int imageCardBack) {
        this.imageCardBack = imageCardBack;
    }

    public int getImageCardLogo() {
        return imageCardLogo;
    }

    public void setImageCardLogo(int imageCardLogo) {
        this.imageCardLogo = imageCardLogo;
    }

    public String getFirstFour() {
        return firstFour;
    }

    public void setFirstFour(String firstFour) {
        this.firstFour = firstFour;
    }

    public String getFourthFour() {
        return fourthFour;
    }

    public void setFourthFour(String fourthFour) {
        this.fourthFour = fourthFour;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getHolderExpireDate() {
        return holderExpireDate;
    }

    public void setHolderExpireDate(String holderExpireDate) {
        this.holderExpireDate = holderExpireDate;
    }
}
