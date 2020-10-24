package com.example.eventbrowser;

public class FavoriteFeed {

    private String favName;
    private String favMap;
    private String favLoc;
    private String favDesc;
    private String favDate;
    private String favImage;
    private String favKey;

    public FavoriteFeed(){

    }


    public FavoriteFeed(String favName, String favMap, String favLoc, String favDesc, String favDate, String favImage, String favKey) {
        this.favName = favName;
        this.favMap = favMap;
        this.favLoc = favLoc;
        this.favDesc = favDesc;
        this.favDate = favDate;
        this.favImage = favImage;
        this.favKey = favKey;
    }

    public String getFavName() {
        return favName;
    }

    public String getFavMap() {
        return favMap;
    }

    public String getFavLoc() {
        return favLoc;
    }

    public String getFavDesc() {
        return favDesc;
    }

    public String getFavDate() {
        return favDate;
    }

    public String getFavImage() {
        return favImage;
    }

    public String getFavKey() {
        return favKey;
    }
}
