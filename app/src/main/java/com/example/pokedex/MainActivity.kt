package com.example.pokedex

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var pokemonRecyclerAdapter = PokemonRecyclerAdapter()
    lateinit var viewModel: MainViewModel
    var offset = 0
    var readyToLoad = false
    private val pokemonObserver = Observer<PokemonInfo.PokemonList> {
        pokemonRecyclerAdapter.addToListPokemon(it.results)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = pokemonRecyclerAdapter
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (readyToLoad) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            offset += 20
                            subscribe()
                        }
                    }
                }
            }
        })
        readyToLoad = true
        subscribe()
    }

    private fun subscribe() {
        viewModel.getPokemonsLiveData(offset)
            .observe(this, pokemonObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.searchable, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: androidx.appcompat.widget.SearchView =
            searchItem?.actionView as androidx.appcompat.widget.SearchView


        searchView.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pokemonRecyclerAdapter.filter.filter(newText)
                readyToLoad = false
                if (newText.isNullOrEmpty()) {
                    readyToLoad = true
                }
                return false
            }

        })
        return true
    }
}
