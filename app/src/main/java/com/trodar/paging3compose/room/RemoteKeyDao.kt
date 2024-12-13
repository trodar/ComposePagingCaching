package com.trodar.paging3compose.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trodar.paging3compose.room.model.RemoteKeys

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("Select * From remote_key Where imbd_id = :imbdId")
    suspend fun getRemoteKeyByMovieID(imbdId: String): RemoteKeys?

    @Query("Delete From remote_key")
    suspend fun clearRemoteKeys()

}