package com.example.pokemonuniverse.model.pojo;

import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;

import com.example.pokemonuniverse.model.pojo.stats.PokemonStat;
import com.example.pokemonuniverse.utils.BitmapConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable, Comparable<Pokemon>{

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
    }

    @Override
    public int compareTo(Pokemon pokemon) {
        PokemonAdditionalInf current = this.getAdditionalInf();
        PokemonAdditionalInf other = pokemon.additionalInf;

        int comparison = current.getPokemonAttack() - other.getPokemonAttack();
        if (comparison != 0) {
            return comparison;
        }
        comparison = current.getPokemonHp() - other.getPokemonHp();
        if (comparison != 0 ) {
            return comparison;
        }
        return current.getPokemonDefence() - other.getPokemonDefence();
    }
}
