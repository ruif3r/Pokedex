package com.example.pokedex.repository

import androidx.paging.PagingSource
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.pokeapi.PokeApiService

class PokePagingSource(private val pokeApi: PokeApiService) : PagingSource<Int, PokemonInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonInfo> {
        val pageNumber = params.key ?: 0
        val pokeList = pokeApi.getPokemonList(pageNumber)
        return LoadResult.Page(pokeList.pokemons, null, pageNumber + 20)
    }
}