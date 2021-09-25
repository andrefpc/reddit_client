package com.andrefpc.mocks

import com.andrefpc.data.ApiResult
import com.andrefpc.data.RedditChild
import com.andrefpc.repository.RedditRepository

class MockRedditRepositoryImpl : RedditRepository {
    private var postsResponse: ApiResult<List<RedditChild>> = ApiResult.Success(emptyList())

    fun setSuccessResponse(post: List<RedditChild>) {
        postsResponse = ApiResult.Success(post)
    }

    fun setErrorResponse() {
        postsResponse = ApiResult.Error
    }

    override suspend fun getPosts(lastItemName: String?): ApiResult<List<RedditChild>> {
        return postsResponse
    }
}