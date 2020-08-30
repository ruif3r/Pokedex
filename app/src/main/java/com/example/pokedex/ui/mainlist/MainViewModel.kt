package com.example.pokedex.ui.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pokedex.repository.PokeRepository

class MainViewModel(private val repo: PokeRepository) : ViewModel() {

    val pokemonList = repo.getPokemonList().liveData.cachedIn(viewModelScope)

}

