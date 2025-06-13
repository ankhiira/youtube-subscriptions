package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model

import com.gabchmel.youtubesubscriptions.subscription.domain.model.SortOrder
import com.gabchmel.youtubesubscriptions.subscription.domain.model.Subscription

data class SubscriptionListState(
    val isLoading: Boolean = false,
    val sortOrder: SortOrder = SortOrder.RELEVANCE,
    val subscriptions: List<Subscription> = emptyList(),
    val filteredSubscriptions: List<Subscription> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)