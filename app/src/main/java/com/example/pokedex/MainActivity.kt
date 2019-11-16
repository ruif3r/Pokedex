package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.PokemonInfo
import com.example.pokedex.models.PokemonResponse
import com.example.pokedex.pokeapi.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var recyclerView: RecyclerView
    lateinit var pokemonRecyclerAdapter: PokemonRecyclerAdapter
    var offset = 0
    var readyToLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        pokemonRecyclerAdapter = PokemonRecyclerAdapter()
        recyclerView.adapter = pokemonRecyclerAdapter
        recyclerView.setHasFixedSize(true)
        val layourManager: GridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layourManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                            getData(offset)
                        }
                    }
                }
            }
        })

        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        getData(offset)
    }

    fun getData(offset: Int) {
        val service: PokeApiService = retrofit.create(PokeApiService::class.java)
        val pokemonResponseCall: Call<PokemonResponse> = service.getPokemonList(offset)
        pokemonResponseCall.enqueue(object : Callback<PokemonResponse> {
            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                readyToLoad = true
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Error")
                alertDialog.setMessage("${t.message}")
            }

            override fun onResponse(
                call: Call<PokemonResponse>, response: Response<PokemonResponse>
            ) {
                readyToLoad = true
                if (response.isSuccessful) {
                    val pokemonResponse: PokemonResponse? = response.body()
                    val pokemonList = pokemonResponse?.results
                    pokemonRecyclerAdapter.addToListPokemon(pokemonList)
                } else {
                    val alertDialog = AlertDialog.Builder(this@MainActivity)
                    alertDialog.setTitle("Error")
                    alertDialog.setMessage("${response.errorBody()}")
                    alertDialog.create().show()
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.searchable, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: androidx.appcompat.widget.SearchView = searchItem?.actionView as androidx.appcompat.widget.SearchView


        searchView.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pokemonRecyclerAdapter.filter.filter(newText)
                return false
            }

        })
        return true
    }
}
