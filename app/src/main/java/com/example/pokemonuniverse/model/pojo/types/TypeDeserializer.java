package com.example.pokemonuniverse.model.pojo.types;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class TypeDeserializer implements JsonDeserializer<PokemonType> {

    @Override
    public PokemonType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement jsonElement = json.getAsJsonObject().get("type");
        return new Gson().fromJson(jsonElement, typeOfT);
    }
}
