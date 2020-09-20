package com.example.pokedex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pokedex.NetworkResponse
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.pokeapi.PokeApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val pokeApi: PokeApi
) {

    fun getPokemonList() = Pager(
        PagingConfig(pageSize = 20), pagingSourceFactory = { PokePagingSource(pokeApi) })

    suspend fun getPokemonDetails(id: Int): NetworkResponse<PokemonInfo> {

        return try {
            NetworkResponse.Success(pokeApi.getPokemonDetail(id))
        } catch (e: IOException) {
            NetworkResponse.Error(e)
        } catch (e: HttpException) {
            NetworkResponse.Error(e)
        }
    }

    suspend fun getPokemonSpecies(id: Int): NetworkResponse<PokemonInfo.PokemonSpecies> {

        return try {
            NetworkResponse.Success(pokeApi.getPokemonSpecies(id))
        } catch (e: IOException) {
            NetworkResponse.Error(e)
        } catch (e: HttpException) {
            NetworkResponse.Error(e)
        }
    }

}