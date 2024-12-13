package com.trodar.paging3compose.model

data class MovieSearch(
    override val imdbId: String,
    override val title: String,
    override val year: String,
    override val type: String,
    override val poster: String,
): MovieModel