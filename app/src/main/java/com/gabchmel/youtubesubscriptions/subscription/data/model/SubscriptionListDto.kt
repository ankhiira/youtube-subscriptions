package com.gabchmel.youtubesubscriptions.subscription.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionListDto(
    @SerialName("kind")
    val kind: String? = null,

    @SerialName("etag")
    val etag: String? = null,

    @SerialName("nextPageToken")
    val nextPageToken: String? = null,

    @SerialName("prevPageToken")
    val prevPageToken: String? = null,

    @SerialName("pageInfo")
    val pageInfo: PageInfoDto? = null,

    @SerialName("items")
    val items: List<SubscriptionItemDto> = emptyList()
)

@Serializable
data class PageInfoDto(
    @SerialName("totalResults")
    val totalResults: Int,

    @SerialName("resultsPerPage")
    val resultsPerPage: Int
)

@Serializable
data class SubscriptionItemDto(
    @SerialName("id")
    val id: String,

    @SerialName("snippet")
    val snippet: SubscriptionSnippetDto
)

@Serializable
data class SubscriptionSnippetDto(
    @SerialName("publishedAt")
    val publishedAt: String,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("resourceId")
    val resourceId: ResourceIdDto,

    @SerialName("thumbnails")
    val thumbnails: ThumbnailsDto
)

@Serializable
data class ResourceIdDto(
    @SerialName("channelId")
    val channelId: String
)

@Serializable
data class ThumbnailsDto(
    @SerialName("default")
    val default: ThumbnailDetailDto,
    @SerialName("medium")
    val medium: ThumbnailDetailDto,
    @SerialName("high")
    val high: ThumbnailDetailDto
)

@Serializable
data class ThumbnailDetailDto(
    @SerialName("url")
    val url: String,

    @SerialName("width")
    val width: Int = 0,

    @SerialName("height")
    val height: Int = 0
)