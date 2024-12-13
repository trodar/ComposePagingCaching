package com.trodar.paging3compose.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trodar.paging3compose.model.MovieDetail
import com.trodar.paging3compose.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted private val imdbId: String,
    movieRepository: MovieRepository,
) : ViewModel() {

    val uiState: StateFlow<DetailUiState> =
        movieRepository
            .getMovieDetail(imdbId)
            .map { detail ->
                DetailUiState.Success(detail)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DetailUiState.Loading
            )

    @AssistedFactory
    interface Factory {
        fun create(imdbId: String): DetailViewModel
    }
}

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Error(val exception: Exception) : DetailUiState
    data class Success(val movieDetail: MovieDetail) : DetailUiState
}