package com.andrefpc.repository

import com.andrefpc.data.ApiResult
import com.andrefpc.data.RedditChild

interface RedditRepository {
    suspend fun getPosts(limit: String, lastItemName: String): ApiResult<List<RedditChild>>
}