package com.example.pokemonuniverse.model.pojo.stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonStat {
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
}
