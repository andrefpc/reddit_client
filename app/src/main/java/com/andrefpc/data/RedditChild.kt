package com.andrefpc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RedditChild(
    @SerializedName("data") val data: RedditData,
    @Expose var read: Boolean = false
): Serializable
