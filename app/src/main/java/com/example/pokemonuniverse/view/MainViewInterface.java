package com.example.pokemonuniverse.view;

import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.model.pojo.PokemonAdditionalInf;

public interface MainViewInterface {
    void setAdapter(Adapter adapter);
    void runOnUi(Runnable action);
    void openPokemonActivity(Pokemon pokemon);
}
