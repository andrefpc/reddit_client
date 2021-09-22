package com.andrefpc.api

import com.andrefpc.data.RedditResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RedditApi {
    /**
     * Get Reddit posts
     */
    @GET("/top.json")
    suspend fun getDeliverer(@QueryMap params: HashMap<String, String>): Response<RedditResult>
}