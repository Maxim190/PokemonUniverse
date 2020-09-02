package com.example.pokemonuniverse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokemon {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {this.url = url;}

    public String getName() {
        return name;
    }

    public String getUrl() {return url;}

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }


}
