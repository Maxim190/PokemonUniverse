package com.example.pokemonuniverse.model.api;

import com.example.pokemonuniverse.model.pojo.PokemonAdditionalInf;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkServiceApi {
    @GET("pokemon")
    Call<NetworkServiceArrayResponse> getPokemonsList(@Query("limit") int limit, @Query("offset") int offset);
    @GET("pokemon/{id}")
    Call<PokemonAdditionalInf> getPokemonAdditionalInf(@Path("id") int id);
}
