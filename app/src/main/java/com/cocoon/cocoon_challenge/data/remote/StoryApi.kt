package com.cocoon.cocoon_challenge.data.remote

import com.cocoon.cocoon_challenge.BuildConfig
import com.cocoon.cocoon_challenge.data.remote.response.StoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StoryApi {

    /**
     * API_KEY was set in gradle.properties file
     * As recieved from NY-Times Developer account
     */
    @GET("svc/topstories/v2/home.json")
    suspend fun fetchStories(
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): StoryResponse
}