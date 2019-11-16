package com.example.pokedex.pokeapi

import com.example.pokedex.models.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    fun getPokemonList(@Query("offset") offset : Int) : Call<PokemonResponse>
}