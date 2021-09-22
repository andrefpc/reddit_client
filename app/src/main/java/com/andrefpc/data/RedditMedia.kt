package com.andrefpc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RedditMedia(
    @SerializedName("reddit_video") var video: RedditVideo
): Serializable
