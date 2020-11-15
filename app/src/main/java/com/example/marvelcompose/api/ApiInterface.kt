package com.example.marvelcompose.api

import com.example.marvelcompose.models.ComicsResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/v1/public/comics?ts=1&limit=25&orderBy=-onsaleDate&apikey=3d3ce5daa8ec0f7c17afc52bb68f15f7&hash=a45bdb0bf57b06e72ad4c2c5854e2843")
    fun getComicsData(@Query("offset") offset: String,
                      @Query("title") title: String?
    ): Call<ComicsResponse>

}