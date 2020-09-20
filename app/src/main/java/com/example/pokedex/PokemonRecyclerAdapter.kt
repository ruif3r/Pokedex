package com.example.pokedex

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.ui.detailed_pokemon.PokemonDetailActivity
import com.squareup.picasso.Picasso

class PokemonRecyclerAdapter :
    PagingDataAdapter<PokemonInfo, PokemonRecyclerAdapter.ViewHolder>(
        PokemonComparator
    ) {
    val ID_EXTRA_NAME = "id"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.pokemonName.text = pokemon?.name
        holder.pokemonNumber.text = "#${pokemon?.getPokemonNumber()}"
        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon?.getPokemonNumber()}.png")
            .into(holder.photoImageView)
        holder.photoImageView.setOnClickListener {
            it.context.startActivity(
                Intent(
                    holder.photoImageView.context,
                    PokemonDetailActivity::class.java
                ).putExtra(ID_EXTRA_NAME, pokemon?.getPokemonNumber())
            )
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoImageView: ImageView = itemView.findViewById(R.id.pokemonView)
        var pokemonName: TextView = itemView.findViewById(R.id.nameView)
        var pokemonNumber: TextView = itemView.findViewById(R.id.pokemonNumberView)
    }


    object PokemonComparator : DiffUtil.ItemCallback<PokemonInfo>() {
        override fun areItemsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem == newItem
        }
    }
}