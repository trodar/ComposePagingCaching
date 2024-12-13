package com.trodar.paging3compose.network

import com.trodar.paging3compose.network.model.MovieDetailNetworkModel
import com.trodar.paging3compose.network.model.MovieSearchNetworkModel
import com.trodar.paging3compose.network.model.ResponseOmd
import retrofit2.Response
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val omdbApi: OmdbApi,

) : DataSource {
    override suspend fun getMovieSearch(options: Map<String, String>): ResponseOmd<List<MovieSearchNetworkModel>> =
        omdbApi.getVideoPageList(options)

    override suspend fun getMovieDetail(imdbId: String): Response<MovieDetailNetworkModel> =
        omdbApi.getVideoDetail(imdbId)
}