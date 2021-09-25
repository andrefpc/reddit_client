package com.andrefpc.instrumented_mocks

import com.andrefpc.data.*

object MockPosts {
    fun getPosts(): List<RedditChild> {
        return listOf(
            RedditChild(data = RedditData(
                name = "Test Image",
                title = "Test Image",
                author = "Test Image",
                subreddit = "Test Image",
                created = 123456789,
                thumbnail = "https://www.tecnoveste.com.br/wp-content/uploads/2019/10/reddit-o-que-e-para-que-serve-e-como-funciona-internet-twitter-meme.jpg",
                numComments = 100,
                media = null,
                postHint = "image",
                url = "https://www.tecnoveste.com.br/wp-content/uploads/2019/10/reddit-o-que-e-para-que-serve-e-como-funciona-internet-twitter-meme.jpg")
            ),
            RedditChild(data = RedditData(
                name = "Test Video",
                title = "Test Video",
                author = "Test Video",
                subreddit = "Test Video",
                created = 123456789,
                thumbnail = "https://www.tecnoveste.com.br/wp-content/uploads/2019/10/reddit-o-que-e-para-que-serve-e-como-funciona-internet-twitter-meme.jpg",
                numComments = 100,
                media = RedditMedia(
                    video = RedditVideo(
                        url = "https://v.redd.it/skzaf1er1gp71/DASH_720.mp4?source=fallback",
                        width = 576,
                        height = 720
                    ),
                    embed = null
                ),
                postHint = "video",
                url = "https://www.tecnoveste.com.br/wp-content/uploads/2019/10/reddit-o-que-e-para-que-serve-e-como-funciona-internet-twitter-meme.jpg")
            ),
            RedditChild(data = RedditData(
                name = "Test Embed",
                title = "Test Embed",
                author = "Test Embed",
                subreddit = "Test Embed",
                created = 123456789,
                thumbnail = "https://www.tecnoveste.com.br/wp-content/uploads/2019/10/reddit-o-que-e-para-que-serve-e-como-funciona-internet-twitter-meme.jpg",
                numComments = 100,
                media = RedditMedia(
                    video = null,
                    embed = RedditEmbed(
                        providerUrl = "https://gfycat.com",
                        url = "https://thumbs.gfycat.com/SevereJoyfulIceblueredtopzebra-size_restricted.gif",
                        width = 444,
                        height = 250
                    )
                ),
                postHint = "embed",
                url = "https://www.tecnoveste.com.br/wp-content/uploads/2019/10/reddit-o-que-e-para-que-serve-e-como-funciona-internet-twitter-meme.jpg")
            ),
            RedditChild(data = RedditData(
                name = "Test Link",
                title = "Test Link",
                author = "Test Link",
                subreddit = "Test Link",
                created = 123456789,
                thumbnail = "https://www.tecnoveste.com.br/wp-content/uploads/2019/10/reddit-o-que-e-para-que-serve-e-como-funciona-internet-twitter-meme.jpg",
                numComments = 100,
                media = null,
                postHint = "link",
                url = "https://www.reddit.com/")
            ),
            RedditChild(data = RedditData(
                name = "Test Without Thumb",
                title = "Test Without Thumb",
                author = "Test Without Thumb",
                subreddit = "Test Without Thumb",
                created = 123456789,
                thumbnail = "https://www.reddit.com/",
                numComments = 100,
                media = null,
                postHint = "link",
                url = "https://www.reddit.com/")
            ),
        )
    }
}