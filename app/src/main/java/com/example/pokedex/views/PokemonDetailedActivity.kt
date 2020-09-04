package com.example.pokedex.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.viewmodels.PokemonDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_detailed.*
import java.util.*

class PokemonDetailedActivity : AppCompatActivity() {

    private val viewModel  by lazy{
        ViewModelProvider(this).get(PokemonDetailsViewModel::class.java)
    }
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detailed)
        id = intent.getIntExtra("id", 1)

        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(pokemonDetailImageView)
        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png")
            .into(backPokemonImageView)

        subscribeToDetailsLiveData()
        subscribeToSpeciesLiveData()
    }

    private fun subscribeToDetailsLiveData() {
        val pokemonDetailObserver = Observer<PokemonInfo> { pokemonDetail ->
            pokemonNameTextView.text = pokemonDetail.name
            weightTextView.text = pokemonDetail.weight.toString()
            heightTextView.text = pokemonDetail.height.toString()
            baseExperienceTextView.text = pokemonDetail.base_experience.toString()
            for (i in pokemonDetail.types) {
                val iterator = pokemonDetail.types.iterator()
                if (iterator.hasNext()) {
                    typeTextView.append("${i.type.name}/")
                } else {
                    typeTextView.append(i.type.name)
                }
            }
        }
        viewModel.getPokemonMainDetailsLiveData(id).observe(this, pokemonDetailObserver)
    }

    private fun subscribeToSpeciesLiveData() {
        val pokemonSpeciesObserver = Observer<PokemonInfo.PokemonSpecies> { pokemonSpecies ->
            evolves_from_textView.text =
                pokemonSpecies?.evolves_from_species?.name?.capitalize() ?: "N/A"
            generation_TextView.text = pokemonSpecies.generation.name
            flavor_textView.text =
                pokemonSpecies.flavor_text_entries[pokemonSpecies.flavor_text_entries.indexOfFirst {
                    it.language.name.equals(
                        Locale.getDefault().language
                    )
                }].flavor_text
        }
        viewModel.getPokemonSpeciesLiveData(id).observe(this, pokemonSpeciesObserver)
    }
}
