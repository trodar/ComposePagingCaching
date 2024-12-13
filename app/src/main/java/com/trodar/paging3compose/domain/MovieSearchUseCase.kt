package com.trodar.paging3compose.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.trodar.paging3compose.model.MovieModel
import com.trodar.paging3compose.network.DataSource
import com.trodar.paging3compose.repository.MovieRemoteMediator
import com.trodar.paging3compose.room.MovieDao
import com.trodar.paging3compose.room.MovieDataBase
import com.trodar.paging3compose.room.RemoteKeyDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class MovieSearchUseCase @Inject constructor(
    private val movieDao: MovieDao,
    private val dataSource: DataSource,
    private val dataBase: MovieDataBase,
    private val remoteKeyDao: RemoteKeyDao,
) {
    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(
        movieName: String
    ): Flow<PagingData<MovieModel>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = DISTANCE,
            initialLoadSize = PAGE_SIZE,
        ),
        pagingSourceFactory = {
            movieDao.getMovies()
        },
        remoteMediator = MovieRemoteMediator(
            movieName = movieName,
            movieDao = movieDao,
            movieDataSource = dataSource,
            movieDataBase =  dataBase,
            remoteKeyDao = remoteKeyDao,
        )
    ).flow.map {
        it.map { entity -> entity }
    }

    companion object{
        const val PAGE_SIZE = 20
        const val DISTANCE = 2
    }
}