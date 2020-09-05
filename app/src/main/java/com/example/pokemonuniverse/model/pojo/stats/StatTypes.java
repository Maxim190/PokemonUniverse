package com.example.pokemonuniverse.model.pojo.stats;

public enum StatTypes {
    HP("hp"),
    ATTACK("attack"),
    DEFENSE("defense");

    public String name;

    StatTypes(String name) {
        this.name = name;
    }
}
