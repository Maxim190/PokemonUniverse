package com.example.pokemonuniverse.presenter;

import android.util.Log;

import androidx.lifecycle.Observer;

import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.model.Pokemon;
import com.example.pokemonuniverse.model.PokemonStorage;
import com.example.pokemonuniverse.view.MainViewInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;

public class MainPresenter implements MainPresenterInterface {

    PokemonStorage pokemonStorage;
    MainViewInterface view;
    Adapter mainViewAdapter;

    public MainPresenter(MainViewInterface view) {
        this.view = view;
        pokemonStorage = new PokemonStorage();
        mainViewAdapter = new Adapter(pokemonStorage, new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getPortionOfPokemon();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("PokemonError", e.getMessage());
            }
        });
        view.setAdapter(mainViewAdapter);
        getPortionOfPokemon();
    }

    private void getPortionOfPokemon() {
        pokemonStorage.getNextListOfPokemons(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mainViewAdapter.dataChanged();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("PokemonError", e.getMessage());
            }
        });
    }
}
