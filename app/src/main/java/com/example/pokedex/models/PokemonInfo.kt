package com.example.pokedex.models

class PokemonInfo (var name : String, var url : String?, var id : Int){
    var weight = 0
    var height = 0
    var base_experience = 0

    constructor(name: String, url: String, id: Int, weight : Int, height : Int, base_experience : Int) : this(name, url, id){
        this.weight = weight
        this.height = height
        this.base_experience = base_experience
    }

    fun getNumbers() : Int{ //si le quito la s al metodo me marca un error con el parametro number y no se por que

        val urlParts = url?.split("/")
        return Integer.parseInt(urlParts!!.get(urlParts.size-2))
    }
}
