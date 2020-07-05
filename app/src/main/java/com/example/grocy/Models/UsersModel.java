package com.example.grocy.Models;

public class UsersModel {

    String profilePic;
    String fName;
    String id;

    public UsersModel() {
    }

    public UsersModel(String profilePic, String fName, String id) {
        this.profilePic = profilePic;
        this.fName = fName;
        this.id = id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
