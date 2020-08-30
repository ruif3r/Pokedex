package com.example.pokedex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pokedex.pokeapi.PokeApiService
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val pokeApi: PokeApiService
) {

    fun getPokemonList() = Pager(
        PagingConfig(pageSize = 20), pagingSourceFactory = { PokePagingSource(pokeApi) })
}