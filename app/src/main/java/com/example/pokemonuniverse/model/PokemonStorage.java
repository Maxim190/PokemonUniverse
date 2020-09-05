package com.example.pokemonuniverse.model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.pokemonuniverse.model.api.NetworkService;
import com.example.pokemonuniverse.model.api.NetworkServiceArrayResponse;
import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.model.pojo.PokemonAdditionalInf;
import com.example.pokemonuniverse.utils.Consts;
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

    public Pokemon getPokemonByPosition(int position) {
        return storage.get(position);
    }

    public Integer getStorageSize() {
        return storage.size();
    }

    public List<Pokemon> getPokemonList() {
        return storage;
    }

    public void loadNextPartOfPokemons(Observer<Pokemon> observer) {
        NetworkService.getInstance()
                .getServiceApi()
                .getPokemonsList(PORTION_SIZE, storage.size())
                .enqueue(new Callback<NetworkServiceArrayResponse>() {
                    @Override
                    public void onResponse(Call<NetworkServiceArrayResponse> call,
                                           Response<NetworkServiceArrayResponse> response) {
                        List<Pokemon> rawList = response.body().getResults();
                        storage.addAll(rawList);

                        Observable<Pokemon> observable = Observable.create(emitter -> {
                            rawList.forEach(pokemon -> {
                                Bitmap image = loadImageFromUrl(String.format(IMAGE_STORE_URL_TMP, pokemon.getId()));
                                pokemon.setImage(image);
                                emitter.onNext(pokemon);
                            });
                            emitter.onComplete();
                        });
                        observable.subscribeOn(Schedulers.io())
                                .subscribeWith(observer);
                    }

                    @Override
                    public void onFailure(Call<NetworkServiceArrayResponse> call, Throwable t) {
                        Log.e(Consts.LOG_ERROR_TAG, t.getMessage());
                    }
                });
    }

    public void loadPokemonAdditionalInf(Pokemon pokemon, Observer<Pokemon> observer) {
        if (pokemon.getAdditionalInf() != null) {
            Observable.just(pokemon).subscribeWith(observer);
            return;
        }
        NetworkService.getInstance()
                .getServiceApi()
                .getPokemonAdditionalInf(pokemon.getId())
                .enqueue(new Callback<PokemonAdditionalInf>() {
                    @Override
                    public void onResponse(Call<PokemonAdditionalInf> call, Response<PokemonAdditionalInf> response) {
                        pokemon.setAdditionalInf(response.body());
                        Observable.just(pokemon).subscribeWith(observer);
                    }

                    @Override
                    public void onFailure(Call<PokemonAdditionalInf> call, Throwable t) {

                    }
                });
    }

    public Bitmap loadImageFromUrl(String url) {
        Bitmap result = null;
        try {
            result = Picasso.get().load(url).get();
        } catch (IOException e) {
            Log.e(Consts.LOG_ERROR_TAG, e.getMessage());
        }
        return result;
    }
}
