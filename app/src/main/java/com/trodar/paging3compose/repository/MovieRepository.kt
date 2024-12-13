package com.trodar.paging3compose.repository

import com.trodar.paging3compose.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovieDetail(imdbId: String): Flow<MovieDetail>
}