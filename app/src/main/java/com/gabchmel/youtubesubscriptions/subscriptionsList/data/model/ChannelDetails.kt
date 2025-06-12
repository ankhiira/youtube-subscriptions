package com.gabchmel.youtubesubscriptions.subscriptionsList.data.model

data class ChannelDetails(
    val id: String,
    val name: String,
    val description: String,
    val bannerUrl: String?,
    val subscriberCount: String
)