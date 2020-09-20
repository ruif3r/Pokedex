package com.example.pokedex.di

import com.example.pokedex.repository.PokeRepository
import com.example.pokedex.ui.detailed_pokemon.PokemonDetailActivity
import com.example.pokedex.ui.mainlist.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun repository(): PokeRepository

    fun inject(mainActivity: MainActivity)

    fun inject(detailActivity: PokemonDetailActivity)
}