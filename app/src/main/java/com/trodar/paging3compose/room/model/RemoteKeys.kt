package com.trodar.paging3compose.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_key")
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo("imbd_id")
    val imdbId: String,
    @ColumnInfo("current_page")
    val currentPage: Int,
    @ColumnInfo("prev_key")
    val prevKey: Int?,
    @ColumnInfo("next_key")
    val nextKey: Int?,
)
