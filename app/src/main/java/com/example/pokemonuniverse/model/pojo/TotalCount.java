package com.example.pokemonuniverse.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalCount {
    @SerializedName("count")
    @Expose
    private Integer totalCount;

    public TotalCount(Integer value) {
        totalCount = value;
    }

    public Integer getTotalCount() {
        return totalCount;
    }
}
