package com.example.pokedex

import android.app.Application
import com.example.pokedex.di.DaggerApplicationComponent

class App : Application() {

    val applicationComponent by lazy { DaggerApplicationComponent.create() }

    override fun onCreate() {
        super.onCreate()
    }
}