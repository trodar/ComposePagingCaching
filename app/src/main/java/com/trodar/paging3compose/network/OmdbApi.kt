package com.trodar.paging3compose.network

import com.trodar.paging3compose.network.model.MovieDetailNetworkModel
import com.trodar.paging3compose.network.model.MovieSearchNetworkModel
import com.trodar.paging3compose.network.model.ResponseOmd
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface OmdbApi {

    @GET(".")
    suspend fun getVideoPageList(
        @QueryMap options: Map<String, String>,
        @Query("apikey") apikey: String = API_KEY,
    ): ResponseOmd<List<MovieSearchNetworkModel>>

    @GET(".")
    suspend fun getVideoDetail(
        @Query("i") imdbId: String,
        @Query("apikey") apikey: String = API_KEY,
    ): Response<MovieDetailNetworkModel>

    companion object {
        const val BASE_HTTP_URL = "https://www.omdbapi.com/"
        const val API_KEY = "YOUR API KEY"
    }
}