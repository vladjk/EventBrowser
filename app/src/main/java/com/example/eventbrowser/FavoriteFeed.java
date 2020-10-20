package com.example.eventbrowser;

public class FavoriteFeed {
    private String nameFav;
    private String mapFav;
    private String locFav;
    private String descFav;
    private String dateFav;
    private String imageFav;

    public FavoriteFeed(String nameFav) {
        this.nameFav = nameFav;
        this.mapFav = mapFav;
        this.locFav = locFav;
        this.descFav = descFav;
        this.dateFav = dateFav;
        this.imageFav = imageFav;
    }

    public String getNameFav() {
        return nameFav;
    }

    public void setNameFav(String nameFav) {
        this.nameFav = nameFav;
    }

    public String getMapFav() {
        return mapFav;
    }

    public void setMapFav(String mapFav) {
        this.mapFav = mapFav;
    }

    public String getLocFav() {
        return locFav;
    }

    public void setLocFav(String locFav) {
        this.locFav = locFav;
    }

    public String getDescFav() {
        return descFav;
    }

    public void setDescFav(String descFav) {
        this.descFav = descFav;
    }

    public String getDateFav() {
        return dateFav;
    }

    public void setDateFav(String dateFav) {
        this.dateFav = dateFav;
    }

    public String getImageFav() {
        return imageFav;
    }

    public void setImageFav(String imageFav) {
        this.imageFav = imageFav;
    }


}
