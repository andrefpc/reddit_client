package com.andrefpc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RedditData(
    @SerializedName("name") val name: String,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("subreddit") val subreddit: String,
    @SerializedName("created") val created: Long,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("num_comments") val numComments: String,
    @SerializedName("secure_media") val media: RedditMedia
) : Serializable
