package com.example.pokemonuniverse.presenter;

import android.util.Log;

import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.adapter.AdapterEvent;
import com.example.pokemonuniverse.model.PokemonStorage;
import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.model.pojo.PokemonAdditionalInf;
import com.example.pokemonuniverse.model.pojo.stats.StatTypes;
import com.example.pokemonuniverse.utils.Consts;
import com.example.pokemonuniverse.view.MainViewInterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public void filterPokemonsByStats(List<StatTypes> filters) {
        List<Pokemon> pokemons = pokemonStorage.getPokemonList();
        new Thread(()-> {
            final int[] counter = {0};
            pokemons.forEach(pokemon -> pokemonStorage.loadPokemonAdditionalInf(pokemon, new Observer<Pokemon>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Pokemon pokemon) {
                    counter[0]++;
                    if ((pokemonStorage.getStorageSize()) == counter[0]) {
                        Collections.sort(pokemonStorage.getPokemonList(), new PokemonStatComparator(filters));
                        view.runOnUi(()-> mainViewAdapter.refreshAll());
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            }));
        }).start();
    }

    private class PokemonStatComparator implements Comparator<Pokemon> {

        List<StatTypes> filters;

        public PokemonStatComparator(List<StatTypes> filters) {
            this.filters = filters;
        }

        @Override
        public int compare(Pokemon o1, Pokemon o2) {
            PokemonAdditionalInf current = o1.getAdditionalInf();
            PokemonAdditionalInf other = o2.getAdditionalInf();

            int comparison = 0;
            if (filters.contains(StatTypes.ATTACK)) {
                comparison = other.getPokemonAttack() - current.getPokemonAttack();
                if (comparison != 0) {
                    return comparison;
                }
            }
            else if (filters.contains(StatTypes.HP)) {
                comparison = other.getPokemonHp() - current.getPokemonHp();
                if (comparison != 0) {
                    return comparison;
                }
            }
            else if (filters.contains(StatTypes.DEFENSE)) {
                return other.getPokemonDefence() - current.getPokemonDefence();
            }
            else {
                return o1.getId() - o2.getId();
            }
            return comparison;
        }
    }
}
