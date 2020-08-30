package com.example.pokedex.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(@SerializedName("results") var pokemons: ArrayList<PokemonInfo>)

