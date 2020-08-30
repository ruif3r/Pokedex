package com.example.pokedex.ui.mainlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.App
import com.example.pokedex.PokemonRecyclerAdapter
import com.example.pokedex.R
import com.example.pokedex.models.PokemonInfo
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val pokemonRecyclerAdapter = PokemonRecyclerAdapter()
    private lateinit var currentPokemon: ItemSnapshotList<PokemonInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).applicationComponent.inject(this)
        setupRecyclerView()
        subscribingToPagingSource(null)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = pokemonRecyclerAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun subscribingToPagingSource(search: String?) {
        viewModel.pokemonList.observe(this, Observer { pagingData ->
            if (!search.isNullOrEmpty()) {
                val currentPokemonList = currentPokemon.items
                pokemonRecyclerAdapter.submitData(
                    lifecycle,
                    PagingData.from(currentPokemonList.filter {
                        it.name.contains(
                            search.trim(),
                            true
                        )
                    })
                )
            } else pokemonRecyclerAdapter.submitData(lifecycle, pagingData)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.searchable, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                currentPokemon = pokemonRecyclerAdapter.snapshot()
            }
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                subscribingToPagingSource(newText)
                return false
            }
        })
        return true
    }
}
