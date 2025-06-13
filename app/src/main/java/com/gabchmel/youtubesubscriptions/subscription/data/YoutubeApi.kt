package com.gabchmel.youtubesubscriptions.subscription.data

import com.gabchmel.youtubesubscriptions.subscription.data.model.SubscriptionListDto
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Query

interface YoutubeApi {

    @GET("youtube/v3/subscriptions")
    suspend fun getSubscriptions(
        @Header("Authorization") authHeader: String,
        @Query("part") part: String = "snippet",
        @Query("mine") mine: Boolean = true,
        @Query("maxResults") maxResults: Int = 50,
        @Query("order") order: String = "relevance"
    ): SubscriptionListDto

    @GET("youtube/v3/subscriptions")
    suspend fun getSubscriptionById(
        @Header("Authorization") authHeader: String,
        @Query("part") part: String = "snippet",
        @Query("id") id: String
    ): SubscriptionListDto
}