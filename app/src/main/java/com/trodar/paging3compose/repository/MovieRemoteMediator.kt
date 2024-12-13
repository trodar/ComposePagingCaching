package com.trodar.paging3compose.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.trodar.paging3compose.network.DataSource
import com.trodar.paging3compose.network.model.asEntityModel
import com.trodar.paging3compose.room.MovieDao
import com.trodar.paging3compose.room.MovieDataBase
import com.trodar.paging3compose.room.RemoteKeyDao
import com.trodar.paging3compose.room.model.MovieSearchEntity
import com.trodar.paging3compose.room.model.RemoteKeys
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieDataSource: DataSource,
    private val movieName: String,
    private val movieDao: MovieDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val movieDataBase: MovieDataBase,
) : RemoteMediator<Int, MovieSearchEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyLast(state: PagingState<Int, MovieSearchEntity>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            remoteKeyDao.getRemoteKeyByMovieID(movie.imdbId)
        }
    }

    private suspend fun getRemoteKeyCurrentPosition(state: PagingState<Int, MovieSearchEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.imdbId?.let { id ->
                remoteKeyDao.getRemoteKeyByMovieID(id)
            }
        }
    }

    private suspend fun getRemoteKeyFirst(state: PagingState<Int, MovieSearchEntity>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            remoteKeyDao.getRemoteKeyByMovieID(movie.imdbId)
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieSearchEntity>
    ): MediatorResult {

        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyFirst(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyLast(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = movieDataSource.getMovieSearch(
                mapOf(
                    "s" to movieName,
                    "page" to page.toString()
                )
            )
            // for testing progress indicator
            delay(1000L)


            val movies = apiResponse.result
            val endOfPaginationReached = movies?.isEmpty() ?: true

            movieDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearRemoteKeys()
                    movieDao.deleteAllMovies()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies?.map {
                    RemoteKeys(
                        imdbId = it.imdbId,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                if (remoteKeys != null)
                    remoteKeyDao.insertAll(remoteKeys)
                if (movies != null)
                    movieDao
                        .upsertMovies(movies.map { it.asEntityModel(page) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }

    }

}