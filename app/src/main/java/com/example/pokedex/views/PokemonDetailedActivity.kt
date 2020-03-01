package com.example.pokedex.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.pokedex.R
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.pokeapi.PokeApiAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_detailed.*
import retrofit2.*

class PokemonDetailedActivity : AppCompatActivity() {

    lateinit var pokemonDetail: PokemonInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detailed)

        val id = intent.getIntExtra("id", 0)
        getDetails(id = id)

        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(pokemonDetailImageView)
        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png")
            .into(backPokemonImageView)
    }

    fun getDetails(id: Int) {
        val pokemonResponseCall: Call<PokemonInfo> = PokeApiAdapter.pokeApi.getPokemonDetail(id)
        pokemonResponseCall.enqueue(object : Callback<PokemonInfo> {
            override fun onFailure(call: Call<PokemonInfo>, t: Throwable) {
                val alertDialog = AlertDialog.Builder(this@PokemonDetailedActivity)
                alertDialog.setTitle("Error")
                alertDialog.setMessage("${t.message}")
                alertDialog.create().show()
            }

            override fun onResponse(call: Call<PokemonInfo>, response: Response<PokemonInfo>) {
                if (response.isSuccessful) {
                    val pokemonResponse: PokemonInfo? = response.body()
                    pokemonDetail = PokemonInfo(pokemonResponse!!.name, "https://pokeapi.co/api/v2/pokemon/$id", pokemonResponse.id,
                        pokemonResponse.weight, pokemonResponse.height, pokemonResponse.base_experience, pokemonResponse.types, pokemonResponse.capture_rate, pokemonResponse.evolves_from_species)
                    pokemonNameTextView.text = pokemonDetail.name
                    weightTextView.text = pokemonDetail.weight.toString()
                    heightTextView.text = pokemonDetail.height.toString()
                    baseExperienceTextView.text = pokemonDetail.base_experience.toString()
                    for (i in pokemonDetail.types){
                        val iterator = pokemonDetail.types.iterator()
                        if (iterator.hasNext()){
                            typeTextView.append("${i.type.name}/")
                        }else{
                            typeTextView.append(i.type.name)
                        }
                    }
                }

            }
        })
    }
}
