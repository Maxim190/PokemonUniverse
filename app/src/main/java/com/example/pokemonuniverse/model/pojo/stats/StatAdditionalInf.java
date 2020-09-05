package com.example.pokemonuniverse.model.pojo.stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatAdditionalInf implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
}
