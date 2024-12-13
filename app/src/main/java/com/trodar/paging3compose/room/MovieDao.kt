package com.trodar.paging3compose.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.trodar.paging3compose.room.model.MovieSearchEntity

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovies(movies: List<MovieSearchEntity>)

    @Query("Select * from movie order by page")
    fun getMovies(): PagingSource<Int, MovieSearchEntity>

    @Query("Delete from movie")
    suspend fun deleteAllMovies()
}