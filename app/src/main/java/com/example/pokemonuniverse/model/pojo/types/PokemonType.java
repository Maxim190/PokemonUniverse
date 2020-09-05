package com.example.pokemonuniverse.model.pojo.types;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PokemonType  implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
