package com.gabchmel.youtubesubscriptions.subscription.data.mapper

import com.gabchmel.youtubesubscriptions.subscription.data.model.SubscriptionItemDto
import com.gabchmel.youtubesubscriptions.subscription.domain.model.SubscriptionDetail
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_detail.utils.convertUtcIsoToLocalDateTimeString

fun SubscriptionItemDto.toSubscriptionDetail(): SubscriptionDetail {
    return SubscriptionDetail(
        title = this.snippet.title,
        description = this.snippet.description,
        thumbnailUrl = this.snippet.thumbnails.high.url,
        publishedAt = convertUtcIsoToLocalDateTimeString(this.snippet.publishedAt)
    )
}