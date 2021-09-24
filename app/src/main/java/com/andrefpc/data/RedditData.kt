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
    @SerializedName("num_comments") val numComments: Int,
    @SerializedName("secure_media") val media: RedditMedia?,
    @SerializedName("post_hint") val postHint: String?,
    @SerializedName("url") val url: String
) : Serializable {
    fun getTime(): String {
        val now = System.currentTimeMillis()
        val diff = now - (this.created * 1000)
        val minutes: Long = diff / 1000 / 60
        if (minutes < 60) {
            return "$minutes minutes ago"
        }
        val hours: Long = minutes / 60
        if (hours < 24) {
            return "$hours hours ago"
        }
        val days: Long = hours / 24
        return "$days days ago"
    }

    fun getComments(): String {
        return when (numComments) {
            0 -> {
                "Without comments"
            }
            1 -> {
                "$numComments comment"
            }
            else -> {
                "$numComments comments"
            }
        }
    }

    fun getPostedBy(): String {
        return "Posted by $subreddit"
    }
}
