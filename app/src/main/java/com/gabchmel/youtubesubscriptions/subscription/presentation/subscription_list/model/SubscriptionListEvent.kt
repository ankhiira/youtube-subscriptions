package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model

import com.gabchmel.youtubesubscriptions.subscription.domain.model.SortOrder

sealed class SubscriptionListEvent {
    data object LoadSubscriptions : SubscriptionListEvent()
    data class ChangeSortOrder(val newSort: SortOrder) : SubscriptionListEvent()
    data class SearchQueryChanged(val newQuery: String) : SubscriptionListEvent()
}