package com.trodar.paging3compose.network.model

import com.google.gson.annotations.SerializedName
import com.trodar.paging3compose.model.MovieDetail

data class MovieDetailNetworkModel(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Rated")
    val rated: String,
    @SerializedName("Released")
    val released: String,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Awards")
    val awards: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("BoxOffice")
    val boxOffice: String?,
)


fun MovieDetailNetworkModel.asExternalModel() = MovieDetail(
    title = title,
    year = year,
    rated = rated,
    released = released,
    runtime = runtime,
    genre = genre,
    director = director,
    actors = actors,
    plot = plot,
    country = country,
    awards = awards,
    poster = poster,
    imdbId = imdbID ,
    boxOffice = boxOffice ?: "N/A",
)


