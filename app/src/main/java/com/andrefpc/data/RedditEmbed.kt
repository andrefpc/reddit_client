package com.andrefpc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RedditEmbed(
    @SerializedName("provider_url") var providerUrl: String,
    @SerializedName("thumbnail_url") var url: String,
    @SerializedName("thumbnail_width") var width: Int,
    @SerializedName("thumbnail_height") var height: Int
): Serializable
