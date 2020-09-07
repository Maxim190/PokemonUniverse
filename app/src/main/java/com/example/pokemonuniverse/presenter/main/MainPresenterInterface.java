package com.example.pokemonuniverse.presenter.main;

import com.example.pokemonuniverse.model.pojo.stats.StatTypes;

import java.util.List;

public interface MainPresenterInterface {
    void filterPokemonsByStats(List<StatTypes> filters, Boolean scrollToBeginning);
    void initializeListWithNewSeed();
    void loadNewPortionOfData();
}
