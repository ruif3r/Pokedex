package com.example.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.repository.PokeRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val repo: PokeRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokeRepository::class.java).newInstance(repo)
    }
}