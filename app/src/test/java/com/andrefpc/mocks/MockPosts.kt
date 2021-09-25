package com.andrefpc.mocks

import com.andrefpc.data.RedditChild
import com.andrefpc.data.RedditData

object MockPosts {
    fun getImagePost(): List<RedditChild> {
        return listOf(RedditChild(data = RedditData(
            name = "Test",
            title = "Test",
            author = "Test",
            subreddit = "Test",
            created = 123456789,
            thumbnail = "https://www.google.com.br/images/branding/googlelogo/2x/googlelogo_color_160x56dp.png",
            numComments = 100,
            media = null,
            postHint = "image",
            url = "https://www.google.com.br/images/branding/googlelogo/2x/googlelogo_color_160x56dp.png"
        )))
    }
}