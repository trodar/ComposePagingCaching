package com.trodar.paging3compose.network.model

import com.google.gson.annotations.SerializedName
import com.trodar.paging3compose.model.MovieModel
import com.trodar.paging3compose.model.MovieSearch
import com.trodar.paging3compose.room.model.MovieSearchEntity

data class MovieSearchNetworkModel(
    @SerializedName("Title")
    override val title: String,
    @SerializedName("Year")
    override val year: String,
    @SerializedName("imdbID")
    override val imdbId: String,
    @SerializedName("Type")
    override val type: String,
    @SerializedName("Poster")
    override val poster: String,
): MovieModel

fun MovieSearchNetworkModel.asEntityModel(page: Int) = MovieSearchEntity(
    title = title,
    year = year,
    imdbId = imdbId,
    type = type,
    poster = poster,
    page = page,
)

