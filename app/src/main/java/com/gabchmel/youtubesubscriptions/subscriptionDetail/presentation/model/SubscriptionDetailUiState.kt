package com.gabchmel.youtubesubscriptions.subscriptionDetail.presentation.model

data class SubscriptionDetailUiState(
    val isLoading: Boolean = false,
    val subscriptionDetail: SubscriptionDetail? = null,
    val error: String? = null
)