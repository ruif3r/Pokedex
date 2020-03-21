package com.example.pokedex.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class PokemonDataSet<T> {

    private lateinit var call: Call<T>

    fun makeCall(call: Call<T>): LiveData<T> {
        this.call = call
        val genericCallBack = GenericCallBack<T>()
        this.call.clone().enqueue(genericCallBack)
        return genericCallBack.result
    }
}

class GenericCallBack<T> : Callback<T> {
    var result: MutableLiveData<T> = MutableLiveData()
    override fun onFailure(call: Call<T>, t: Throwable) {

    }

    override fun onResponse(
        call: Call<T>, response: Response<T>
    ) {
        if (response.isSuccessful) {
            result.value = response.body()
        }
    }
}


