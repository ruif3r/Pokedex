package com.example.pokedex.di

import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.MainViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory
}