package com.example.pokemonuniverse.model;

import androidx.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonStorage extends LiveData<List<Pokemon>> {
    private static final int PORTION_SIZE = 30;

    List<Pokemon> storage;

    public PokemonStorage() {
        storage = new LinkedList<>();
    }

    public Pokemon getPokemonById(int id) {
        return storage.get(id);
    }

    public Integer getStorageSize() {
        return storage.size();
    }

    public void getNextListOfPokemons(DisposableCompletableObserver observer) {
        NetworkService.getInstance()
                .getServiceApi()
                .getPokemonList(PORTION_SIZE, storage.size())
                .enqueue(new Callback<NetworkServiceArrayResponse>() {
                    @Override
                    public void onResponse(Call<NetworkServiceArrayResponse> call,
                                           Response<NetworkServiceArrayResponse> response) {
                            storage.addAll(response.body().data);
                            Completable.complete()
                                    .subscribeWith(observer)
                                    .dispose();
                    }

                    @Override
                    public void onFailure(Call<NetworkServiceArrayResponse> call, Throwable t) {
                        Completable.error(t)
                                .subscribeWith(observer)
                                .dispose();
                    }
                });
    }
}
