package com.trodar.paging3compose.di

import android.content.Context
import androidx.room.Room
import com.trodar.paging3compose.room.MovieDao
import com.trodar.paging3compose.room.MovieDataBase
import com.trodar.paging3compose.room.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesFireChatDatabase(
        @ApplicationContext context: Context
    ): MovieDataBase = Room.databaseBuilder(
        context,
        MovieDataBase::class.java,
        "movie-database",
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(
       movieDataBase: MovieDataBase
    ): MovieDao = movieDataBase.movieDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(
        movieDataBase: MovieDataBase
    ): RemoteKeyDao = movieDataBase.remoteKeyDao()
}