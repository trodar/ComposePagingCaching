package com.trodar.paging3compose.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trodar.paging3compose.room.model.MovieSearchEntity
import com.trodar.paging3compose.room.model.RemoteKeys

@Database(
    entities = [
        MovieSearchEntity::class,
        RemoteKeys::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}