package com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model

data class SubscriptionsListUiState(
    val isLoading: Boolean = false,
    val sortOrder: SortOrder = SortOrder.RELEVANCE,
    val subscriptions: List<SubscriptionUiState> = emptyList(),
    val filteredSubscriptions: List<SubscriptionUiState> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)