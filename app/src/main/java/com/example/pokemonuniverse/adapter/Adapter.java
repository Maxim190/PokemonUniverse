package com.example.pokemonuniverse.adapter;

import android.graphics.Color;
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
    private Boolean selectFirstItem = false;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (selectFirstItem && position == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#7FFFD4"));
        }
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

    public void refreshItem(int position) {
        notifyItemChanged(position);
    }

    public void refreshAll() {
        notifyDataSetChanged();
    }

    public void selectFirstItem(Boolean value) {
        selectFirstItem = value;
        refreshItem(0);
    }

    @Override
    public int getItemCount() {
        return storage.getStorageSize();
    }

    public void setNewStorage(PokemonStorage storage) {
        this.storage = storage;
        refreshAll();
    }

    public Adapter(PokemonStorage storage, Observer<AdapterEvent> eventListener) {
        this.eventListener = eventListener;
        this.storage = storage;
    }
}
