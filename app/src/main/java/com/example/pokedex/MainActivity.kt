package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var pokemonRecyclerAdapter: PokemonRecyclerAdapter
    lateinit var viewModel: MainViewModel
    var offset = 0
    var readyToLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        pokemonRecyclerAdapter = PokemonRecyclerAdapter()
        recyclerView.adapter = pokemonRecyclerAdapter
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
       /* recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = layourManager.childCount
                    val totalItemCount = layourManager.itemCount
                    val pastVisibleItems = layourManager.findFirstVisibleItemPosition()

                    if (readyToLoad) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            readyToLoad = false
                            offset += 20
                            val pokemonObserver = Observer<PokemonInfo.PokemonList> {
                                pokemonRecyclerAdapter.dataset = it.results!!
                            }
                            viewModel.getPokemonsLiveData(offset)
                                .observe(MainActivity(), pokemonObserver)
                        }
                    }
                }
            }
        })*/
        subscribe()
        // readyToLoad = true

       // viewModel.initViewModel()
       // val result = viewModel.getPokemonsLiveData(offset).value?.results!!
        /*if (result != null) {
            pokemonRecyclerAdapter.dataset = result
        } else {

        }*/
       // result?.let { resultNotEmpty ->
         //   pokemonRecyclerAdapter.dataset = resultNotEmpty
        //}
    }

    private fun subscribe() {
        val pokemonObserver = Observer<PokemonInfo.PokemonList> {
            pokemonRecyclerAdapter.addToListPokemon(it.results)
        }
        viewModel.getPokemonsLiveData(offset)
            .observe(MainActivity(), pokemonObserver)
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
