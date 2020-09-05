package com.example.pokemonuniverse.model.pojo;

import android.util.Log;

import com.example.pokemonuniverse.model.pojo.stats.PokemonStat;
import com.example.pokemonuniverse.model.pojo.stats.StatTypes;
import com.example.pokemonuniverse.model.pojo.types.PokemonType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PokemonAdditionalInf implements Serializable {
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

    private Integer getStatValue(StatTypes type) {
        for (PokemonStat stat: stats) {
            if (stat.getName().equals(type.name)) {
                return stat.getValue();
            }
        }
        return 0;
    }

    public Integer getPokemonHp() {
        return getStatValue(StatTypes.HP);
    }

    public Integer getPokemonAttack() {
        return getStatValue(StatTypes.ATTACK);
    }

    public Integer getPokemonDefence() {
        return getStatValue(StatTypes.DEFENSE);
    }
}
