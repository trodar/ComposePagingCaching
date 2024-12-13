package com.trodar.paging3compose.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.trodar.paging3compose.domain.MovieSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    movieSearchUseCase: MovieSearchUseCase

): ViewModel() {
    val moviesState = movieSearchUseCase("fast").cachedIn(viewModelScope)
}