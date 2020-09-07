package com.example.pokedex.di

import com.example.pokedex.pokeapi.PokeApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {


    @Provides
    fun providesRetrofitBuilder() = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun providesPokeApi(retrofit: Retrofit) = retrofit.create(PokeApiService::class.java)
}


