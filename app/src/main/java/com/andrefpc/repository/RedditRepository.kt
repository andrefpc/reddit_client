package com.andrefpc.repository

import com.andrefpc.data.ApiResult
import com.andrefpc.data.RedditChild

interface RedditRepository {
    /**
     * Get Posts from the api
     * @param lastItemName The last post name from the loaded list to pagination (non-required)
     * @return List of [RedditChild]
     */
    suspend fun getPosts(lastItemName: String?): ApiResult<List<RedditChild>>
}