package com.andrefpc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RedditMetadata(
    @SerializedName("after") val lastItemName: String,
    @SerializedName("children") val children: List<RedditChild>,
): Serializable
