package com.example.pokemonuniverse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkServiceArrayResponse {

    @SerializedName("count")
    @Expose
    Integer count;
    @SerializedName("results")
    @Expose
    List<Pokemon> data;

    public Integer getCount() {
        return count;
    }

    public List<Pokemon> getData() {
        return data;
    }

    public NetworkServiceArrayResponse(Integer count, List<Pokemon> data) {
        this.count = count;
        this.data = data;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setData(List<Pokemon> data) {
        this.data = data;
    }
}
