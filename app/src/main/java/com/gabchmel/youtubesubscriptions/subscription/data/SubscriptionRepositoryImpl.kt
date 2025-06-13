package com.gabchmel.youtubesubscriptions.subscription.data

import com.gabchmel.youtubesubscriptions.core.data.TokenProvider
import com.gabchmel.youtubesubscriptions.core.presentation.navigation.Screen
import com.gabchmel.youtubesubscriptions.subscription.data.mapper.toSubscription
import com.gabchmel.youtubesubscriptions.subscription.data.mapper.toSubscriptionDetail
import com.gabchmel.youtubesubscriptions.subscription.domain.SubscriptionRepository
import com.gabchmel.youtubesubscriptions.subscription.domain.model.SortOrder
import com.gabchmel.youtubesubscriptions.subscription.domain.model.Subscription
import com.gabchmel.youtubesubscriptions.subscription.domain.model.SubscriptionDetail

class SubscriptionRepositoryImpl(
    private val youtubeApi: YoutubeApi,
    private val tokenProvider: TokenProvider
) : SubscriptionRepository {
    override suspend fun getSubscriptions(order: SortOrder): List<Subscription> {
        val accessToken = tokenProvider.getToken()
        if (accessToken == null) {
            return emptyList()
        }

        val subscriptionListDto = youtubeApi.getSubscriptions(
            authHeader = "Bearer $accessToken",
            order = when (order) {
                SortOrder.RELEVANCE -> "relevance"
                SortOrder.ALPHABETICALLY -> "alphabetical"
            }
        )
        return subscriptionListDto.items.map { it.toSubscription() }
    }

    override suspend fun getSubscriptionDetails(subscriptionId: String): SubscriptionDetail? {
        val accessToken = tokenProvider.getToken()
        if (accessToken == null) {
            return null
        }

        val subscriptionListDto = youtubeApi.getSubscriptionById(
            authHeader = "Bearer $accessToken",
            id = subscriptionId
        )

        return subscriptionListDto.items.firstOrNull()?.toSubscriptionDetail()
    }
}