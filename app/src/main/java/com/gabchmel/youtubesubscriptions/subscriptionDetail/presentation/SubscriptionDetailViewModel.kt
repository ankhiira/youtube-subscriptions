package com.gabchmel.youtubesubscriptions.subscriptionDetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.profile.presentation.AuthHolder.accessToken
import com.gabchmel.youtubesubscriptions.subscriptionDetail.presentation.model.SubscriptionDetail
import com.gabchmel.youtubesubscriptions.subscriptionDetail.presentation.model.SubscriptionDetailUiState
import com.gabchmel.youtubesubscriptions.subscriptionsList.data.model.SubscriptionItemDto
import com.gabchmel.youtubesubscriptions.subscriptionsList.data.YoutubeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val youtubeApi: YoutubeApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val channelId: String? = savedStateHandle["channelId"]
        if (channelId != null) {
            fetchChannelDetails(channelId)
        } else {
            _uiState.update {
                it.copy(
                    error = "Channel ID not found."
                )
            }
        }
    }

    private fun fetchChannelDetails(channelId: String) {
        viewModelScope.launch {
            try {
                val response = youtubeApi.getSubscription(
                    authHeader = "Bearer ${accessToken.value}",
                    id = channelId
                )

                val uiSubscriptionDetail = response.items.firstOrNull()?.toSubscriptionDetail()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        subscriptionDetail = uiSubscriptionDetail
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Failed to load subscriptions: ${e.message}")
                }
            }
        }
    }
}

fun SubscriptionItemDto.toSubscriptionDetail(): SubscriptionDetail {
    return SubscriptionDetail(
        title = this.snippet.title,
        description = this.snippet.description,
        thumbnailUrl = this.snippet.thumbnails.high.url,
        publishedAt = convertUtcIsoToLocalDateTimeString(this.snippet.publishedAt)
    )
}