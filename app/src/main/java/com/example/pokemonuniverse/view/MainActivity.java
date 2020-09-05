package com.example.pokemonuniverse.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonuniverse.R;
import com.example.pokemonuniverse.model.pojo.stats.StatTypes;
import com.example.pokemonuniverse.utils.Consts;
import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.model.pojo.Pokemon;
import com.example.pokemonuniverse.presenter.MainPresenter;
import com.example.pokemonuniverse.presenter.MainPresenterInterface;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements MainViewInterface{

    private MainPresenterInterface mainPresenter;
    private RecyclerView recyclerView;
    private CheckBox attackCheckBox;
    private CheckBox hpCheckBox;
    private CheckBox defenceCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attackCheckBox = findViewById(R.id.attack_check_box);
        hpCheckBox = findViewById(R.id.hp_check_box);
        defenceCheckBox = findViewById(R.id.defence_check_box);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mainPresenter = new MainPresenter(this);

    }

    public void setAdapter(Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void runOnUi(Runnable action) {
        runOnUiThread(action);
    }

    @Override
    public void openPokemonActivity(Pokemon pokemon) {
        Intent intent = new Intent(this, PokemonActivity.class);
        intent.putExtra(Consts.INTENT_EXTRA, pokemon);

        startActivity(intent);
    }

    public void filterButtonClicked(View view) {
        List<StatTypes> stats = new LinkedList<>();
        if (attackCheckBox.isChecked()) {
            stats.add(StatTypes.ATTACK);
        }
        if (hpCheckBox.isChecked()) {
            stats.add(StatTypes.HP);
        }
        if (defenceCheckBox.isChecked()) {
            stats.add(StatTypes.DEFENSE);
        }
        mainPresenter.filterPokemonsByStats(stats);
    }
}