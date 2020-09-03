package com.example.pokemonuniverse.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkServiceApi {
    @GET("pokemon")
    Call<NetworkServiceArrayResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);
}
