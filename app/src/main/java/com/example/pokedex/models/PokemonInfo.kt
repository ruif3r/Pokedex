package com.example.pokedex.models

data class PokemonInfo(var name: String, var url: String?) {
    var weight = 0
    var height = 0
    var base_experience = 0
    var types = ArrayList<TypeResponse>()
    var capture_rate = 0
    var evolves_from_species: PokemonSpecies? = null

    fun getPokemonNumber(): Int {

        val urlParts = url?.split("/")
        return Integer.parseInt(urlParts!!.get(urlParts.size - 2))
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
