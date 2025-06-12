package com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model

sealed class SubsListEvent {
    data object LoadSubscriptions : SubsListEvent()
    data class ChangeSortOrder(val newSort: SortOrder) : SubsListEvent()
    data class SearchQueryChanged(val newQuery: String) : SubsListEvent()
    data class SubscriptionClicked(val item: SubscriptionUiState) : SubsListEvent()
}