package com.example.pokedex.domain

import com.example.pokedex.data.PokemonDataSet
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.pokeapi.PokeApiAdapter

class PokemonRepository {

    fun getPokemonListCase(offset: Int) =
        PokemonDataSet<PokemonInfo.PokemonList>().makeCall(
            PokeApiAdapter.pokeApi.getPokemonList(offset)
        )

    fun getPokemonDetailsCase(id: Int) =
        PokemonDataSet<PokemonInfo>().makeCall(PokeApiAdapter.pokeApi.getPokemonDetail(id))

    fun getPokemonSpeciesCase(id: Int) =
        PokemonDataSet<PokemonInfo.PokemonSpecies>().makeCall(
            PokeApiAdapter.pokeApi.getPokemonSpecies(id)
        )
}