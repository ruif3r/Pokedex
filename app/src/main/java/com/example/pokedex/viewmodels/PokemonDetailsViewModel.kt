package com.example.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.PokemonRepository

class PokemonDetailsViewModel : ViewModel() {

    private val pokemonRepo = PokemonRepository()

    fun getPokemonMainDetailsLiveData(id : Int) = pokemonRepo.getPokemonDetailsCase(id)

    fun getPokemonSpeciesLiveData(id : Int) = pokemonRepo.getPokemonSpeciesCase(id)
}