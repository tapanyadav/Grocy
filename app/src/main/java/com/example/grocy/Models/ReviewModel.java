package com.example.grocy.Models;

import java.io.Serializable;
import java.util.HashMap;

public class ReviewModel implements Serializable {

    String detailedReview;
    HashMap<String, String> likeData = new HashMap();
    HashMap<String, String> notLikeData = new HashMap();
    double rating;
    String reviewImage;
    long numberOfLikes;
    long numberOfComments;
    String reviewId;
    String shopImage, shopName, shopAddress;

    public ReviewModel(String detailedReview, HashMap<String, String> likeData, HashMap<String, String> notLikeData, double rating, String reviewImage, long numberOfLikes, long numberOfComments, String reviewId, String shopImage, String shopName, String shopAddress) {
        this.detailedReview = detailedReview;
        this.likeData = likeData;
        this.notLikeData = notLikeData;
        this.rating = rating;
        this.reviewImage = reviewImage;
        this.numberOfLikes = numberOfLikes;
        this.numberOfComments = numberOfComments;
        this.reviewId = reviewId;
        this.shopImage = shopImage;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
    }

    public ReviewModel() {
    }


    public String getDetailedReview() {
        return detailedReview;
    }

    public void setDetailedReview(String detailedReview) {
        this.detailedReview = detailedReview;
    }

    public HashMap<String, String> getLikeData() {
        return likeData;
    }

    public void setLikeData(HashMap<String, String> likeData) {
        this.likeData = likeData;
    }

    public HashMap<String, String> getNotLikeData() {
        return notLikeData;
    }

    public void setNotLikeData(HashMap<String, String> notLikeData) {
        this.notLikeData = notLikeData;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }

    public long getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(long numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public long getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(long numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
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
}
