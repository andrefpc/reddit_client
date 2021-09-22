package com.andrefpc.repository

import com.andrefpc.api.RedditApi
import com.andrefpc.data.ApiResult
import com.andrefpc.data.RedditChild
import com.andrefpc.data.RedditResult
import retrofit2.Response

class RedditRepositoryImpl(
    private val redditApi: RedditApi
) : RedditRepository {
    override suspend fun getPosts(
        limit: String,
        lastItemName: String
    ): ApiResult<List<RedditChild>> {
        val params: HashMap<String, String> = hashMapOf(
            "limit" to limit,
            "after" to lastItemName
        )
        val response: Response<RedditResult> = redditApi.getPosts(params)
        if (!response.isSuccessful) {
            return ApiResult.Error
        }

        response.body()?.let {
            val children = it.data.children
            return ApiResult.Success(children)
        } ?: kotlin.run {
            return ApiResult.Error
        }
    }
}