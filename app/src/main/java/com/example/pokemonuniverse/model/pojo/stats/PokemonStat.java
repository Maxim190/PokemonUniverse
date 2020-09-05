package com.example.pokemonuniverse.model.pojo.stats;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PokemonStat implements Serializable {
    @SerializedName("base_stat")
    @Expose
    private Integer value;
    @SerializedName("stat")
    @Expose
    private StatAdditionalInf additionalInf;

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return additionalInf.getName();
    }

    @NonNull
    @Override
    public String toString() {
        return value + " " + getName();
    }
}
