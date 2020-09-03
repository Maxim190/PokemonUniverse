package com.example.pokemonuniverse.model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.pokemonuniverse.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonStorage extends LiveData<List<Pokemon>> {
    private static final int PORTION_SIZE = 30;
    public static final String IMAGE_STORE_URL_TMP = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/%d.png";
    private List<Pokemon> storage;

    public PokemonStorage() {
        storage = new LinkedList<>();
    }

    public Pokemon getPokemonById(int id) {
        return storage.get(id);
    }

    public Integer getStorageSize() {
        return storage.size();
    }

    public void loadNextPartOfPokemons(Observer<Pokemon> observer) {
        NetworkService.getInstance()
                .getServiceApi()
                .getPokemonList(PORTION_SIZE, storage.size())
                .enqueue(new Callback<NetworkServiceArrayResponse>() {
                    @Override
                    public void onResponse(Call<NetworkServiceArrayResponse> call,
                                           Response<NetworkServiceArrayResponse> response) {
                        List<Pokemon> rawList = response.body().results;
                        storage.addAll(response.body().results);

                        Observable<Pokemon> observable = Observable.create(emitter ->
                                rawList.forEach(pokemon -> {
                                    Bitmap image = loadImageFromUrl(String.format(IMAGE_STORE_URL_TMP, pokemon.getId()));
                                    pokemon.setImage(image);
                                    emitter.onNext(pokemon);
                                })
                        );
                        observable.subscribeOn(Schedulers.io())
                                .subscribeWith(observer);
                    }

                    @Override
                    public void onFailure(Call<NetworkServiceArrayResponse> call, Throwable t) {
                        Log.e(Utils.LOG_ERROR_TAG, t.getMessage());
                    }
                });
    }

    public Bitmap loadImageFromUrl(String url) {
        Bitmap result = null;
        try {
            result =  Picasso.get().load(url).get();
        } catch (IOException e) {
            Log.e(Utils.LOG_ERROR_TAG, e.getMessage());
        }
        return result;
    }
}
