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

class MoviesScreenViewModel : ViewModel() {
    private val _comicsData = MutableLiveData<List<ComicsModel>?>(null)
    val comicsData: LiveData<List<ComicsModel>?> = _comicsData

    private val _comicsSearchData = MutableLiveData<String?>(null)
    val comicsSearchData: LiveData<String?> = _comicsSearchData

    var currentOffset = 0
    private var totalDataCount = 0
    private var isFetching = false

    init {
        fetchMovies()
    }

    fun updateComicsSearchData(newState: String) {
        _comicsSearchData.value = newState
        resetData()
        fetchMovies()
    }

    private fun resetData() {
        currentOffset = 0
        totalDataCount = 0
        _comicsData.postValue(null)
    }

    fun fetchMovies() {
        isFetching = true

        var searchQuery = _comicsSearchData.value
        searchQuery?.let {
            if (it.isEmpty()) searchQuery = null
        }

        val apiInterface = ServiceGenerator.createService(ApiInterface::class.java)
        val call = apiInterface.getComicsData((currentOffset + 1).toString(), searchQuery)
        call.enqueue(object : Callback<ComicsResponse> {
            override fun onResponse(
                call: Call<ComicsResponse>,
                response: Response<ComicsResponse>
            ) {
                val newComicsData = if (comicsData.value == null)
                    response.body()?.data?.results!!
                else comicsData.value!! + response.body()?.data?.results!!

                _comicsData.postValue(newComicsData)

                currentOffset += response.body()?.data?.count!!
                totalDataCount = response.body()?.data?.total!!
                isFetching = false
            }

            override fun onFailure(call: Call<ComicsResponse>, t: Throwable) {
                isFetching = false
            }

        })
    }

    fun canFetchData(): Boolean = currentOffset + 1 < totalDataCount && !isFetching

    fun getComicsById(comicsId: String?): ComicsModel? {
        return comicsData.value?.firstOrNull { comics -> comics.id.toString() == comicsId }
    }
}