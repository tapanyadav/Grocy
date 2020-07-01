package com.example.grocy.Models;

import java.util.Date;

public class CommentModel {
    String commentUserName;
    String commentUserImage;
    Date commentTime;
    String commentData;

    public CommentModel(String commentUserName, String commentUserImage, Date commentTime, String commentData) {
        this.commentUserName = commentUserName;
        this.commentUserImage = commentUserImage;
        this.commentTime = commentTime;
        this.commentData = commentData;
    }

    public CommentModel() {
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentUserImage() {
        return commentUserImage;
    }

    public void setCommentUserImage(String commentUserImage) {
        this.commentUserImage = commentUserImage;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentData() {
        return commentData;
    }

    public void setCommentData(String commentData) {
        this.commentData = commentData;
    }
}
