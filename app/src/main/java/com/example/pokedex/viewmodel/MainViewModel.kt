package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.PokemonRepository

class MainViewModel : ViewModel() {

    private val pokemonRepo = PokemonRepository()

    fun getPokemonsLiveData(offset: Int) = pokemonRepo.getPokemonListCase(offset)

}