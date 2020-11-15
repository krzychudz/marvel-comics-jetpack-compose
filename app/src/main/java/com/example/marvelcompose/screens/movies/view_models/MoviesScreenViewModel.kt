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
    private val _comicsData = MutableLiveData<List<ComicsModel>>(listOf())
    val comicsData: LiveData<List<ComicsModel>> = _comicsData

    var currentOffset = 0
    private var totalDataCount = 0

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        val apiInterface = ServiceGenerator.createService(ApiInterface::class.java)
        val call = apiInterface.getComicsData((currentOffset + 1).toString(), null)
        call.enqueue(object: Callback<ComicsResponse> {
            override fun onResponse(
                call: Call<ComicsResponse>,
                response: Response<ComicsResponse>
            ) {
                comicsData.value?.let { currentData ->
                    val newComicsData = currentData + response.body()?.data?.results!!
                    _comicsData.postValue(newComicsData)
                    currentOffset += response.body()?.data?.count!!
                    totalDataCount = response.body()?.data?.total!!
                }
            }

            override fun onFailure(call: Call<ComicsResponse>, t: Throwable) {
                Log.d("Fetched", t.message.toString())
            }

        })
    }

    fun isDataToFetch(): Boolean = currentOffset < totalDataCount
}