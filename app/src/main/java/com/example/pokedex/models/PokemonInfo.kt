package com.example.pokedex.models

data class PokemonInfo (var name : String, var url : String?, var id : Int){
    var weight = 0
    var height = 0
    var base_experience = 0
    var types = ArrayList<TypeResponse>()
    var capture_rate = 0
    var evolves_from_species : PokemonSpecies? = null

    constructor(name: String, url: String, id: Int, weight : Int, height : Int, base_experience : Int, types : ArrayList<TypeResponse>, capture_rate: Int, evolves_from_species: PokemonSpecies?) : this(name, url, id){
        this.weight = weight
        this.height = height
        this.base_experience = base_experience
        this.types = types
        this.capture_rate = capture_rate
        this.evolves_from_species = evolves_from_species
    }

    fun getNumbers() : Int{ //si le quito la s al metodo me marca un error con el parametro number y no se por que

        val urlParts = url?.split("/")
        return Integer.parseInt(urlParts!!.get(urlParts.size-2))
    }

    data class PokemonList(var results: ArrayList<PokemonInfo>?)

    data class PokemonType(var name : String)

    data class TypeResponse(var type : PokemonType)

    data class PokemonSpecies(var capture_rate : Int, var evolves_from_species : EvolvesFrom, var flavor_text_entries: ArrayList<FlavorText>,
    var generation : OriginalGeneration)

    data class EvolvesFrom(var name : String)

    data class FlavorText(var flavor_text : String, var language : Language)

    data class Language(var name: String)

    data class OriginalGeneration(var name : String)
}
