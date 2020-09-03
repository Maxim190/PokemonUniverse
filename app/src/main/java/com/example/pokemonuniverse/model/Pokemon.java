package com.example.pokemonuniverse.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokemon {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    private Integer id;
    private Bitmap image = null;

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Integer getId() {
        if (id == null && url != null && !url.isEmpty()) {
            String[] parsedUrl = url.split("/");
            id = Integer.parseInt(parsedUrl[parsedUrl.length - 1]);
        }
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {this.url = url;}

    public String getName() {
        return name;
    }

    public String getUrl() {return url;}

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
