package com.trodar.paging3compose.network

import com.trodar.paging3compose.network.model.MovieDetailNetworkModel
import com.trodar.paging3compose.network.model.MovieSearchNetworkModel
import com.trodar.paging3compose.network.model.ResponseOmd
import retrofit2.Response

interface DataSource {

    suspend fun getMovieSearch(
        options: Map<String, String>
    ): ResponseOmd<List<MovieSearchNetworkModel>>

    suspend fun getMovieDetail(
        imdbId: String,
    ): Response<MovieDetailNetworkModel>
}