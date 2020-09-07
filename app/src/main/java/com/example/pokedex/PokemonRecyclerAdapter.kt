package com.example.pokedex

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.PokemonInfo
import com.squareup.picasso.Picasso

class PokemonRecyclerAdapter :
    PagingDataAdapter<PokemonInfo, PokemonRecyclerAdapter.ViewHolder>(
        PokemonComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.pokemonName.text = pokemon?.name
        holder.pokemonNumber.text = "#${pokemon?.getNumbers()}"
        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon?.getNumbers()}.png")
            .into(holder.photoImageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoImageView: ImageView = itemView.findViewById(R.id.pokemonView)
        var pokemonName: TextView = itemView.findViewById(R.id.nameView)
        var pokemonNumber: TextView = itemView.findViewById(R.id.pokemonNumberView)
    }


    object PokemonComparator : DiffUtil.ItemCallback<PokemonInfo>() {
        override fun areItemsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem.number == newItem.number
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem == newItem
        }
    }
}