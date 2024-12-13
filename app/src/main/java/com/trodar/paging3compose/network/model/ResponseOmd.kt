package com.trodar.paging3compose.network.model

import com.google.gson.annotations.SerializedName

class ResponseOmd<T : Any?> {
    @SerializedName("Search")
    val result: T? = null

    @SerializedName("totalResults")
    val totalResults: Int? = null

    @SerializedName("Response")
    val response: Boolean? = null
}