package com.gabchmel.youtubesubscriptions.subsList.presentation.model

sealed class SubsListEvent {
    data object LoadSubscriptions : SubsListEvent()
    data class SubscriptionClicked(val item: SubscriptionUiState) : SubsListEvent()
}