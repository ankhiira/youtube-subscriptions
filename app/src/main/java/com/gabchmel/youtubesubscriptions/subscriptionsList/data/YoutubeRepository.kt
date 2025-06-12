package com.gabchmel.youtubesubscriptions.subscriptionsList.data

import androidx.paging.PagingData
import com.gabchmel.youtubesubscriptions.auth.model.ChannelDetails
import com.gabchmel.youtubesubscriptions.auth.model.Subscription
import com.gabchmel.youtubesubscriptions.auth.model.UserProfile
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model.SubscriptionUiState
import kotlinx.coroutines.flow.Flow

interface YoutubeRepository {
    fun getPagedSubscriptions(order: String): Flow<PagingData<Subscription>>
    suspend fun getChannelDetails(channelId: String): Result<ChannelDetails>
    suspend fun getUserProfile(): Result<UserProfile>
}