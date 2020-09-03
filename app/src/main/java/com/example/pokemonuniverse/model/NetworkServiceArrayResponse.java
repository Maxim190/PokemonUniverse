package com.example.pokemonuniverse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkServiceArrayResponse {

    @SerializedName("results")
    @Expose
    List<Pokemon> results;

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }

    public NetworkServiceArrayResponse(List<Pokemon> data) {
        this.results = data;
    }
}
