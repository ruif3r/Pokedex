package com.example.pokedex.repository

import androidx.paging.PagingSource
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.pokeapi.PokeApi
import java.io.IOException

class PokePagingSource(private val pokeApi: PokeApi) : PagingSource<Int, PokemonInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonInfo> {
        return try {
            val pageNumber = params.key ?: 0
            val pokeList = pokeApi.getPokemonList(pageNumber)
            LoadResult.Page(pokeList.pokemons, null, pageNumber + 20)
        } catch (e: IOException) {

            LoadResult.Error(e)
        }
    }
}