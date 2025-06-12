package com.gabchmel.youtubesubscriptions.subscriptionsList.data

import com.gabchmel.youtubesubscriptions.subscriptionsList.data.model.SubscriptionListResponseDto
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
    ): SubscriptionListResponseDto

    @GET("youtube/v3/subscriptions")
    suspend fun getSubscription(
        @Header("Authorization") authHeader: String,
        @Query("part") part: String = "snippet",
        @Query("id") id: String
    ): SubscriptionListResponseDto
}