package com.gabchmel.youtubesubscriptions.subscription.data.mapper

import com.gabchmel.youtubesubscriptions.subscription.data.model.SubscriptionItemDto
import com.gabchmel.youtubesubscriptions.subscription.domain.model.Subscription

fun SubscriptionItemDto.toSubscription(): Subscription {
    return Subscription(
        id = this.id,
        title = this.snippet.title,
        thumbnailUrl = this.snippet.thumbnails.high.url
    )
}