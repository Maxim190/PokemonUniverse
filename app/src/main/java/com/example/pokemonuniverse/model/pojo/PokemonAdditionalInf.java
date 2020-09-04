package com.example.pokemonuniverse.model.pojo;

import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.model.pojo.stats.PokemonStat;
import com.example.pokemonuniverse.model.pojo.types.PokemonType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonAdditionalInf extends Pokemon {
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("types")
    @Expose
    private List<PokemonType> types;
    @SerializedName("stats")
    @Expose
    private List<PokemonStat> stats;

    public PokemonAdditionalInf(String name, String url) {
        super(name, url);
    }

    public List<PokemonType> getTypes() {
        return types;
    }

    public List<PokemonStat> getStats() {
        return stats;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

}
