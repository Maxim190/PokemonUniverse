package com.example.pokemonuniverse.model.pojo;

import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;

import com.example.pokemonuniverse.utils.BitmapConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pokemon implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    private Integer id;
    private Integer adapterPosition;
    private byte[] image;
    private PokemonAdditionalInf additionalInf;

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Integer getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(Integer adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public PokemonAdditionalInf getAdditionalInf() {
        return additionalInf;
    }

    public void setAdditionalInf(PokemonAdditionalInf additionalInf) {
        this.additionalInf = additionalInf;
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
        return BitmapConverter.toBitmap(image);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImage(Bitmap image) {
        this.image = BitmapConverter.toByteArray(image);
        Log.d("YES", this.image.length + " ARRAY");
    }
}
