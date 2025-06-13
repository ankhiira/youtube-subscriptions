package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_detail.model

import com.gabchmel.youtubesubscriptions.subscription.domain.model.SubscriptionDetail

data class SubscriptionDetailState(
    val isLoading: Boolean = false,
    val subscriptionDetail: SubscriptionDetail? = null,
    val error: String? = null
)