package com.example.pokemonuniverse.model.api;

import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkServiceArrayResponse {

    @SerializedName("results")
    @Expose
    private List<Pokemon> results;

    public List<Pokemon> getResults() {
        return results;
    }

    public NetworkServiceArrayResponse(List<Pokemon> data) {
        this.results = data;
    }
}
