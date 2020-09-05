package com.example.pokemonuniverse.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonuniverse.R;
import com.example.pokemonuniverse.model.PokemonStorage;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;


public class Adapter extends RecyclerView.Adapter<MyViewHolder> {

    private PokemonStorage storage;
    private final Observer<AdapterEvent> eventListener;
    private Integer lastItemClickedPosition;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(storage.getPokemonByPosition(position));
        holder.itemView.setOnClickListener(view -> {
            lastItemClickedPosition = position;
            sendEventToPresenter(AdapterEvent.ITEM_CLICKED, eventListener);
        });
        if (position == storage.getStorageSize() - 1) {
            sendEventToPresenter(AdapterEvent.END_OF_DATA, eventListener);
        }
    }

    public Integer getLastClickedItemPosition() {
        return lastItemClickedPosition;
    }

    private void sendEventToPresenter(AdapterEvent event, Observer<AdapterEvent> observer) {
        Observable.just(event)
                .subscribeWith(observer);
    }

    public void refreshItem(int id) {
        notifyItemChanged(id - 1);
    }

    public void refreshAll() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return storage.getStorageSize();
    }

    public Adapter(PokemonStorage storage, Observer<AdapterEvent> eventListener) {
        this.eventListener = eventListener;
        this.storage = storage;
    }
}
