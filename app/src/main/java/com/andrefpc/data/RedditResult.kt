package com.andrefpc.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RedditResult(
    @SerializedName("data") var data: RedditMetadata
): Serializable
