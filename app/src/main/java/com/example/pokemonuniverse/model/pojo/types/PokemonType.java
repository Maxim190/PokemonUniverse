package com.example.pokemonuniverse.model.pojo.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonType{
    public String getName() {
        return name;
    }

    @SerializedName("name")
    @Expose
    private String name;
}
