package com.trodar.paging3compose.repository

import com.trodar.paging3compose.model.MovieDetail
import com.trodar.paging3compose.network.DataSource
import com.trodar.paging3compose.network.model.asExternalModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val dataSource: DataSource,

) : MovieRepository {
    override fun getMovieDetail(imdbId: String): Flow<MovieDetail> = callbackFlow {
        dataSource.getMovieDetail(imdbId).also { response ->
            if (response.isSuccessful) {
                trySendBlocking(response.body()!!.asExternalModel())
            } else {
                throw Exception("Fail load")
            }
        }

        awaitClose()
    }
}