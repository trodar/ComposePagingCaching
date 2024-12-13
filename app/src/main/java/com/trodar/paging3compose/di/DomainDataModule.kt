package com.trodar.paging3compose.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.trodar.paging3compose.model.MovieSearch
import com.trodar.paging3compose.repository.MovieDetailRepository
import com.trodar.paging3compose.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainDataModule {
    @Binds
    fun bindMovieRepository(
        movieDetailRepository: MovieDetailRepository
    ): MovieRepository
}