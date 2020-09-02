package com.example.pokemonuniverse.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonuniverse.R;
import com.example.pokemonuniverse.model.Pokemon;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView url;
    private TextView name;

    public void bind(Pokemon pokemon) {
        name.setText(pokemon.getName());
        url.setText(pokemon.getUrl());
    }

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        url = itemView.findViewById(R.id.list_url);
        name = itemView.findViewById(R.id.list_name);
    }
}
