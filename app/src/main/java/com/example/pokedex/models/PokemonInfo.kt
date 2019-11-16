package com.example.pokedex.models

class PokemonInfo (var name : String, var url : String, var number : Int){

    fun getNumbers() : Int{ //si le quito la s al metodo me marca un error con el parametro number y no se por que

        val urlParts = url.split("/")
        return Integer.parseInt(urlParts[urlParts.size-2])
    }
}
