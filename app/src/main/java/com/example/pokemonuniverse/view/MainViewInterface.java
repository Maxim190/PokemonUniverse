package com.example.pokemonuniverse.view;

import android.content.Context;

import com.example.pokemonuniverse.adapter.Adapter;

public interface MainViewInterface {
    void setAdapter(Adapter adapter);
    void runOnUi(Runnable action);
}
