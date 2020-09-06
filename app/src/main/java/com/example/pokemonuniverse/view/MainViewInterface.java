package com.example.pokemonuniverse.view;

import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.model.pojo.Pokemon;

public interface MainViewInterface {
    void setAdapter(Adapter adapter);
    void runOnUi(Runnable action);
    void openPokemonActivity(Pokemon pokemon);
    void scrollListToPosition(int position);
    void setVisibleLoadingMsg(Boolean isVisible);
    void isSortingTime(Boolean isSortingNow);
}
