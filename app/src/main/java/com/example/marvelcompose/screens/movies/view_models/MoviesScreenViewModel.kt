package com.example.marvelcompose.screens.movies.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelcompose.api.ApiInterface
import com.example.marvelcompose.api.ServiceGenerator
import com.example.marvelcompose.models.ComicsModel
import com.example.marvelcompose.models.ComicsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesScreenViewModel: ViewModel() {
    val text = "VieModelText"

    private val _comicsData = MutableLiveData<List<ComicsModel>>()
    val comicsData: LiveData<List<ComicsModel>> = _comicsData

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        val apiInterface = ServiceGenerator.createService(ApiInterface::class.java)
        val call = apiInterface.getComicsData("0", null)
        call.enqueue(object: Callback<ComicsResponse> {
            override fun onResponse(
                call: Call<ComicsResponse>,
                response: Response<ComicsResponse>
            ) {
                _comicsData.postValue(response.body()?.data?.results)
            }

            override fun onFailure(call: Call<ComicsResponse>, t: Throwable) {
                Log.d("Fetched", t.message.toString())
            }

        })
    }
}