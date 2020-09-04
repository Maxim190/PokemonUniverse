package com.example.pokemonuniverse.model.api;

import com.example.pokemonuniverse.model.pojo.types.PokemonType;
import com.example.pokemonuniverse.model.pojo.types.TypeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService networkService;
    private static final String URL = "https://pokeapi.co/api/v2/";
    private Retrofit retrofit;

    private NetworkService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PokemonType.class, new TypeDeserializer())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NetworkService getInstance() {
        if (networkService == null) {
            networkService = new NetworkService();
        }
        return networkService;
    }

    public NetworkServiceApi getServiceApi() {
        return retrofit.create(NetworkServiceApi.class);
    }
}
