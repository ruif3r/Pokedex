package com.example.pokedex.pokeapi

import com.example.pokedex.models.PokemonInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    fun getPokemonList(@Query("offset") offset : Int) : Call<PokemonInfo.PokemonList>
    @GET("pokemon/{id}/")
    fun getPokemonDetail(@Path("id") id : Int) : Call<PokemonInfo>
    @GET("pokemon-species/{id}")
    fun getPokemonSpecies(@Path("id") id : Int) : Call<PokemonInfo.PokemonSpecies>

}