package com.gabchmel.youtubesubscriptions.subscription.domain.model

data class SubscriptionDetail(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val publishedAt: String
)