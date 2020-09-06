package com.example.pokemonuniverse.model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.pokemonuniverse.model.api.NetworkService;
import com.example.pokemonuniverse.model.api.NetworkServiceArrayResponse;
import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.model.pojo.PokemonAdditionalInf;
import com.example.pokemonuniverse.utils.Consts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
    private Integer seed;
    private TotalCount totalCount;
    private List<Pokemon> storage;

    public PokemonStorage() {
        this(0);
    }

    public PokemonStorage(Integer seed) {
        totalCount = new TotalCount(1000);
        loadTotalDataCount();
        storage = new LinkedList<>();
        this.seed = seed;
    }

    public Integer getTotalCount() {
        return totalCount.getTotalCount();
    }

    private void loadTotalDataCount() {
        NetworkService.getInstance()
                .getServiceApi()
                .getTotalDataCount()
                .enqueue(new Callback<TotalCount>() {
                    @Override
                    public void onResponse(Call<TotalCount> call, Response<TotalCount> response) {
                        totalCount = response.body();
                    }

                    @Override
                    public void onFailure(Call<TotalCount> call, Throwable t) {

                    }
                });
    }

    public Pokemon getPokemonByPosition(int position) {
        return storage.get(position);
    }

    public Integer getPokemonPosition(Pokemon pokemon) {
        return storage.indexOf(pokemon);
    }

    public Integer getStorageSize() {
        return storage.size();
    }

    public List<Pokemon> getPokemonList() {
        return storage;
    }

    public void loadNextPartOfPokemons(Observer<Pokemon> observer) {
        if (storage.size() == totalCount.getTotalCount()) {
            return;
        }
        Integer offset = seed + storage.size();
        if (seed + storage.size() >= totalCount.getTotalCount()) {
            offset = 0;
        }
        Integer portionSize = PORTION_SIZE;
        if (storage.size() + portionSize >= totalCount.getTotalCount()) {
            portionSize = totalCount.getTotalCount() - storage.size();
        }

        NetworkService.getInstance()
                .getServiceApi()
                .getPokemonsList(portionSize, offset)
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
}
