package com.example.pokedex.pokeapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokeApiAdapter {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val pokeApi: PokeApi by lazy {
        retrofit.create(PokeApi::class.java)
    }
}