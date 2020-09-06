package com.example.pokemonuniverse.adapter;

import android.graphics.Color;
import android.util.Log;
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
        if (position == 0) {
            int color = selectFirstItem ? Color.parseColor("#7FFFD4"): Color.WHITE;
            holder.itemView.setBackgroundColor(color);
        }
        else {
            holder.itemView.setBackgroundColor(Color.WHITE);
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

    // Прежде чем обновить все данные, нужно снять выделение с 0 элемента
    // Т.к. при обновлении всех данных у выделенного элемента может поменяться позиция
    public void refreshAll() {
        selectFirstItem = false;
        refreshItem(0);
        notifyDataSetChanged();
    }

    public void selectFirstItem(Boolean value) {
        if (storage == null || storage.getStorageSize() == 0) {
            return;
        }
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
