package com.gabchmel.youtubesubscriptions.subsList.presentation.model

data class SubsListUiState(
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val subscriptions: List<SubscriptionUiState> = emptyList()
)