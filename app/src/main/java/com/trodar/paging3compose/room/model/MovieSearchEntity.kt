package com.trodar.paging3compose.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trodar.paging3compose.model.MovieModel
import com.trodar.paging3compose.model.MovieSearch

@Entity("movie")
data class MovieSearchEntity(
    @PrimaryKey
    @ColumnInfo("imbd_id")
    override val imdbId: String,
    override val title: String,
    override val year: String,
    override val type: String,
    override val poster: String,
    val page: Int,
): MovieModel
