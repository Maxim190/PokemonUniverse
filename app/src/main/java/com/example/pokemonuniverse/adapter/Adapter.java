package com.example.pokemonuniverse.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonuniverse.R;
import com.example.pokemonuniverse.Utils;
import com.example.pokemonuniverse.model.PokemonStorage;

import io.reactivex.rxjava3.observers.DisposableCompletableObserver;


public class Adapter extends RecyclerView.Adapter<MyViewHolder> {

    private PokemonStorage storage;
    private final DisposableCompletableObserver runOutOfDataObserver;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(storage.getPokemonById(position));
        //adapter informs presenter to load more data
        if (position == storage.getStorageSize() - 1) {
            runOutOfDataObserver.onComplete();
        }
    }

    public void refreshItem(int id) {
        notifyItemChanged(id - 1);
    }

    @Override
    public int getItemCount() {
        return storage.getStorageSize();
    }

    public Adapter(PokemonStorage storage, DisposableCompletableObserver runOutOfDataObserver) {
        this.runOutOfDataObserver = runOutOfDataObserver;
        this.storage = storage;
    }
}
