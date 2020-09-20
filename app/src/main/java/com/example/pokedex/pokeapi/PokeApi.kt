package com.example.pokedex.pokeapi

import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.models.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("offset") offset: Int): PokemonResponse

    @GET("pokemon/{id}/")
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonInfo

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonInfo.PokemonSpecies
}