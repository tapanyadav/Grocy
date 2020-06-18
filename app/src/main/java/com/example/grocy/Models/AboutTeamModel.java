package com.example.grocy.Models;

public class AboutTeamModel {

    private int imageTeam;
    private String nameTeam, teamDesignation;
    private int imageBack;

    public AboutTeamModel(int imageTeam, String nameTeam, String teamDesignation, int imageBack) {
        this.imageTeam = imageTeam;
        this.nameTeam = nameTeam;
        this.teamDesignation = teamDesignation;
        this.imageBack = imageBack;
    }

    public int getImageTeam() {
        return imageTeam;
    }

    public void setImageTeam(int imageTeam) {
        this.imageTeam = imageTeam;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getTeamDesignation() {
        return teamDesignation;
    }

    public void setTeamDesignation(String teamDesignation) {
        this.teamDesignation = teamDesignation;
    }

    public int getImageBack() {
        return imageBack;
    }

    public void setImageBack(int imageBack) {
        this.imageBack = imageBack;
    }
}
