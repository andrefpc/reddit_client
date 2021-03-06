package com.andrefpc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RedditVideo(
    @SerializedName("fallback_url") var url: String,
    @SerializedName("width") var width: Int,
    @SerializedName("height") var height: Int
): Serializable
