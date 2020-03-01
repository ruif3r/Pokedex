package com.example.pokedex

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.views.PokemonDetailedActivity
import com.example.pokedex.views.WebDetailActivity
import com.squareup.picasso.Picasso

class PokemonRecyclerAdapter : RecyclerView.Adapter<PokemonRecyclerAdapter.ViewHolder>(), Filterable {

    var dataset = ArrayList<PokemonInfo>()
    private var datasetForSearch : ArrayList<PokemonInfo> = dataset

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p : PokemonInfo = dataset[position]
        holder.pokemonName.text = p.name
        holder.pokemonNumber.text = "#${p.getNumbers()}"
        Picasso.get()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${p.getNumbers()}.png")
            .into(holder.photoImageView)

        holder.photoImageView.setOnClickListener {
            val intent = Intent(it.context, PokemonDetailedActivity::class.java)
            intent.putExtra("id", p.getNumbers())
            it.context.startActivity(intent)
        }
       /* holder.photoImageView.setOnClickListener {
            val intent = Intent(it.context, WebDetailActivity::class.java)
            intent.putExtra("name", p.name)
            it.context.startActivity(intent)
        }*/
    }

    fun addToListPokemon(pokemonList: ArrayList<PokemonInfo>?) {
        if (pokemonList != null) {
            dataset.addAll(pokemonList)
            datasetForSearch=ArrayList(dataset)
            notifyDataSetChanged()
        }
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoImageView : ImageView = itemView.findViewById(R.id.pokemonView)
        var pokemonName : TextView = itemView.findViewById(R.id.nameView)
        var pokemonNumber : TextView = itemView.findViewById(R.id.pokemonNumberView)

    }

    override fun getFilter(): Filter {
        return myFilter
    }

    private var myFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList : ArrayList<PokemonInfo> = ArrayList()


            if (constraint==null || constraint.isEmpty()){
                filteredList.addAll(datasetForSearch) //Esto hace que no desaparezca el recyclerview
            }else{
                val filterPattern : String = constraint.toString().toLowerCase().trim()

                for (item in datasetForSearch){
                    if (item.name.toLowerCase().contains(filterPattern) || item.getNumbers().toString().contains(filterPattern)){
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values=filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataset.clear()
            dataset.addAll(results?.values as ArrayList<PokemonInfo>)
            notifyDataSetChanged()
        }
    }
}