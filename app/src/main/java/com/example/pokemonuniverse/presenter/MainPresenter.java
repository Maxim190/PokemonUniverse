package com.example.pokemonuniverse.presenter;

import android.util.Log;

import com.example.pokemonuniverse.utils.Consts;
import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.adapter.AdapterEvent;
import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.model.PokemonStorage;
import com.example.pokemonuniverse.view.MainViewInterface;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainPresenter implements MainPresenterInterface {

    PokemonStorage pokemonStorage;
    MainViewInterface view;
    Adapter mainViewAdapter;

    public MainPresenter(MainViewInterface view) {
        this.view = view;
        pokemonStorage = new PokemonStorage();
        mainViewAdapter = new Adapter(pokemonStorage, new Observer<AdapterEvent>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull AdapterEvent event) {
                switch (event) {
                    case END_OF_DATA: getPortionOfPokemon(); break;
                    case ITEM_CLICKED: loadAndDisplayPokemonAdditionalInfByPosition(
                            mainViewAdapter.getLastClickedItemPosition());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.setAdapter(mainViewAdapter);
        getPortionOfPokemon();
    }

    private void loadAndDisplayPokemonAdditionalInfByPosition(Integer position) {
        Pokemon rawPokemon = pokemonStorage.getPokemonByPosition(position);
        if (rawPokemon.getAdditionalInf() != null) {
            view.openPokemonActivity(rawPokemon);
        }
        else {
            pokemonStorage.loadPokemonAdditionalInf(rawPokemon, new Observer<Pokemon>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Pokemon pokemon) {
                    view.openPokemonActivity(pokemon);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
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
                Log.e(Consts.LOG_ERROR_TAG, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
