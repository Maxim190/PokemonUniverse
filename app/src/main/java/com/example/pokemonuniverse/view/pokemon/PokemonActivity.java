package com.example.pokemonuniverse.view.pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokemonuniverse.R;
import com.example.pokemonuniverse.utils.Consts;
import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.model.pojo.PokemonAdditionalInf;

import java.util.List;

public class PokemonActivity extends AppCompatActivity {

    private ImageView image;
    private TextView name;
    private TextView weight;
    private TextView height;
    private TextView types;
    private TextView stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        image = findViewById(R.id.image_view_pokemon_activity);
        name = findViewById(R.id.name_pokemon_activity);
        weight = findViewById(R.id.weight_pokemon_activity);
        height = findViewById(R.id.height_pokemon_activity);
        types = findViewById(R.id.types_pokemon_activity);
        stats = findViewById(R.id.stats_pokemon_activity);

        Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra(Consts.INTENT_EXTRA);
        displayPokemon(pokemon);
    }

    public void displayPokemon(Pokemon pokemon) {
        PokemonAdditionalInf inf = pokemon.getAdditionalInf();

        image.setImageBitmap(pokemon.getImage());
        name.setText(pokemon.getName());
        weight.setText(String.valueOf(inf.getWeight()));
        height.setText(String.valueOf(inf.getHeight()));
        types.setText(buildStr(inf.getTypes()));
        stats.setText(buildStr(inf.getStats()));
    }

    public <T> String buildStr(List<T> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(item -> stringBuilder.append(item + "\n"));
        return stringBuilder.toString();
    }
}