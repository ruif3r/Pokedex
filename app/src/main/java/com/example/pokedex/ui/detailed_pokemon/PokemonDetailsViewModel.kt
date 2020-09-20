package com.example.pokedex.ui.detailed_pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokedex.repository.PokeRepository

class PokemonDetailsViewModel(private val repo: PokeRepository) : ViewModel() {

    fun getPokemonMainDetailsLiveData(id: Int) = liveData { emit(repo.getPokemonDetails(id)) }

    fun getPokemonSpeciesLiveData(id: Int) = liveData { emit(repo.getPokemonSpecies(id)) }
}