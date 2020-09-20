package com.example.pokedex.ui.detailed_pokemon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.App
import com.example.pokedex.NetworkResponse
import com.example.pokedex.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_detailed.*
import java.util.*
import javax.inject.Inject

class PokemonDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<PokemonDetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detailed)
        (applicationContext as App).applicationComponent.inject(this)
        val id = intent.getIntExtra("id", 1)
        spriteLoadWithPicasso(id)
        subscribeToDetailsLiveData(id)
        subscribeToSpeciesLiveData(id)
    }

    private fun spriteLoadWithPicasso(id: Int) {
        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(pokemonDetailImageView)
        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png")
            .into(backPokemonImageView)
    }

    private fun subscribeToDetailsLiveData(id: Int) {
        viewModel.getPokemonMainDetailsLiveData(id).observe(this, { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val pokemonDetail = response.data
                    pokemonNameTextView.text = pokemonDetail.name
                    weightTextView.text = pokemonDetail.weight.toString()
                    heightTextView.text = pokemonDetail.height.toString()
                    baseExperienceTextView.text = pokemonDetail.base_experience.toString()
                    if (pokemonDetail.types.size == 2) {
                        typeTextView.text = getString(
                            R.string.pokemon_types,
                            pokemonDetail.types[0].type.name,
                            pokemonDetail.types[1].type.name
                        )
                    } else {
                        typeTextView.text = pokemonDetail.types[0].type.name.toString()
                    }
                }
                is NetworkResponse.Error -> Toast.makeText(
                    this,
                    response.error.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun subscribeToSpeciesLiveData(id: Int) {
        viewModel.getPokemonSpeciesLiveData(id).observe(this, { response ->
            when (response) {

                is NetworkResponse.Success -> {
                    evolves_from_textView.text =
                        response.data.evolves_from_species?.name?.capitalize(Locale.getDefault())
                            ?: "N/A"
                    generation_TextView.text = response.data.generation.name
                    flavor_textView.text =
                        response.data.flavor_text_entries[response.data.flavor_text_entries.indexOfFirst { flavorText ->
                            flavorText.language.name == Locale.getDefault().language
                        }].flavor_text.replace("\n", " ")
                }
            }
        })
    }
}
