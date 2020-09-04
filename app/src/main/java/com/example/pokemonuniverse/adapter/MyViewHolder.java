package com.example.pokemonuniverse.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonuniverse.R;
import com.example.pokemonuniverse.model.pojo.Pokemon;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageField;
    private TextView nameField;

    public void bind(Pokemon pokemon) {
        nameField.setText(pokemon.getName());
        imageField.setImageBitmap(pokemon.getImage());
    }

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageField = itemView.findViewById(R.id.list_image);
        nameField = itemView.findViewById(R.id.list_name);
    }
}
