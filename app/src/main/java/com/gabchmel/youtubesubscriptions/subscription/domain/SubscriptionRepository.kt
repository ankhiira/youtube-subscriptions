package com.gabchmel.youtubesubscriptions.subscription.domain

import com.gabchmel.youtubesubscriptions.subscription.domain.model.SortOrder
import com.gabchmel.youtubesubscriptions.subscription.domain.model.Subscription
import com.gabchmel.youtubesubscriptions.subscription.domain.model.SubscriptionDetail

interface SubscriptionRepository {
    suspend fun getSubscriptions(order: SortOrder): List<Subscription>
    suspend fun getSubscriptionDetails(subscriptionId: String): SubscriptionDetail?
}