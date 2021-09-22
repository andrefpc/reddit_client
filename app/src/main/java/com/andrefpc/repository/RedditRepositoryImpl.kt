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
        lastItemName: String?
    ): ApiResult<List<RedditChild>> {
        try {
            val params: HashMap<String, String> = if(lastItemName != null) {
                hashMapOf(
                    "limit" to "10",
                    "after" to lastItemName
                )
            }else{
                hashMapOf( "limit" to "10" )
            }

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
        } catch (e: Exception) {
            return ApiResult.Error
        }
    }
}