package com.example.eventbrowser;

public class Feed {
    private String name;
    private String map;
    private String loc;
    private String desc;
    private String date;
    private String image;
    private String key;

    public Feed(String name, String map, String loc, String desc, String date,String image) {
        this.name = name;
        this.map = map;
        this.loc = loc;
        this.desc = desc;
        this.date = date;
        this.image = image;
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    public void setKey(){
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Feed(){

    }


}
