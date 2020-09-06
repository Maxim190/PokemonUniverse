package com.example.pokemonuniverse.presenter;

import android.util.Log;

import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.model.PokemonStorage;
import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.model.pojo.PokemonAdditionalInf;
import com.example.pokemonuniverse.model.pojo.stats.StatTypes;
import com.example.pokemonuniverse.utils.Consts;
import com.example.pokemonuniverse.view.MainViewInterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainPresenter implements MainPresenterInterface {

    private Random random;
    private PokemonStorage pokemonStorage;
    private MainViewInterface view;
    private Adapter mainViewAdapter;

    private Boolean isSortingNow = false;
    private Boolean isLoadingDataNow = false;

    private List<StatTypes> filters;

    public MainPresenter(MainViewInterface view) {
        this.view = view;
        random = new Random();
        pokemonStorage = new PokemonStorage();
        mainViewAdapter = new Adapter(pokemonStorage, itemClickedListener());
        view.setAdapter(mainViewAdapter);
        getPortionOfPokemon();
    }

    private SingleObserver<Integer> itemClickedListener() {
        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Integer itemPosition) {
                loadAndDisplayPokemonAdditionalInfByPosition(itemPosition);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        };
    }

    private void setSortingStatus(Boolean value) {
        isSortingNow = value;
        view.runOnUi(()-> view.isSortingTime(value));
    }

    private void setLoadingStatus(Boolean value) {
        isLoadingDataNow = value;
        view.runOnUi(()-> view.setVisibleLoadingMsg(value));
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
        setLoadingStatus(true);
        pokemonStorage.loadNextPartOfPokemons(new Observer<Pokemon>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Pokemon pokemon) {
                view.runOnUi(()-> mainViewAdapter.refreshItem(pokemonStorage.getPokemonPosition(pokemon)));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(Consts.LOG_ERROR_TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                setLoadingStatus(false);
                if (filters != null && !filters.isEmpty()) {
                    filterPokemonsByStats(filters, false);
                }
            }
        });
    }

    public void filterPokemonsByStats(List<StatTypes> filters, Boolean scrollToBeginning) {
        this.filters = filters;
        setSortingStatus(true);
        new Thread(()-> {
            AtomicInteger counter = new AtomicInteger(0);
            pokemonStorage.getPokemonList().forEach(pokemon ->
                    pokemonStorage.loadPokemonAdditionalInf(pokemon, new Observer<Pokemon>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Pokemon pokemon) {
                    if ((pokemonStorage.getStorageSize()) == counter.incrementAndGet()) {
                        Collections.sort(pokemonStorage.getPokemonList(), new PokemonStatComparator(filters));

                        view.runOnUi(()-> {
                            mainViewAdapter.refreshAll();
                            mainViewAdapter.selectFirstItem(!filters.isEmpty());
                            if (scrollToBeginning) {
                                view.scrollListToPosition(0);
                            }
                        });
                        setSortingStatus(false);
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

    @Override
    public void initializeListWithNewSeed() {
        Integer seed = random.nextInt(pokemonStorage.getTotalCount());
        pokemonStorage = new PokemonStorage(seed);
        getPortionOfPokemon();
        view.runOnUi(()-> mainViewAdapter.setNewStorage(pokemonStorage));
    }

    @Override
    public void loadNewPortionOfData() {
        if (!isLoadingDataNow && !isSortingNow) {
            getPortionOfPokemon();
        }
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
            if (filters == null || filters.isEmpty()) {
                return o1.getId() - o2.getId();
            }
            else if (filters.contains(StatTypes.ATTACK)) {
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
            return other.getPokemonDefence() - current.getPokemonDefence();
        }
    }
}
