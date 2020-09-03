package com.example.pokemonuniverse.presenter;

import android.util.Log;

import com.example.pokemonuniverse.Utils;
import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.model.Pokemon;
import com.example.pokemonuniverse.model.PokemonStorage;
import com.example.pokemonuniverse.view.MainViewInterface;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;

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
                Log.e(Utils.LOG_ERROR_TAG, e.getMessage());
            }
        });
        view.setAdapter(mainViewAdapter);
        getPortionOfPokemon();
    }

    private void getPortionOfPokemon() {
        pokemonStorage.loadNextPartOfPokemons(new Observer<Pokemon>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Pokemon pokemon) {
                view.runOnUi(()-> mainViewAdapter.refreshItem(pokemon.getId()));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(Utils.LOG_ERROR_TAG, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
