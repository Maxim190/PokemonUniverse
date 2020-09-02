package com.example.pokemonuniverse.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonuniverse.R;
import com.example.pokemonuniverse.adapter.Adapter;
import com.example.pokemonuniverse.presenter.MainPresenter;
import com.example.pokemonuniverse.presenter.MainPresenterInterface;

public class MainActivity extends AppCompatActivity  implements MainViewInterface{

    private MainPresenterInterface mainPresenter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainPresenter = new MainPresenter(this);

    }

    public void setAdapter(Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

}