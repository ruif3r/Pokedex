package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.PokemonDataUseCase

class MainViewModel : ViewModel() {

    private val useCase = PokemonDataUseCase()

    fun getPokemonsLiveData(offset: Int) = useCase.getPokemonListCase(offset)

}