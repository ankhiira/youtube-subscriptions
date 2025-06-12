//package com.gabchmel.youtubesubscriptions.subscriptionsList
//
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import com.gabchmel.youtubesubscriptions.auth.model.ChannelDetails
//import com.gabchmel.youtubesubscriptions.auth.model.Subscription
//import com.gabchmel.youtubesubscriptions.subscriptionsList.data.AuthManager
//import com.gabchmel.youtubesubscriptions.subscriptionsList.data.YouTubeApi
//import com.gabchmel.youtubesubscriptions.subscriptionsList.data.YoutubeRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//class YoutubeRepositoryImpl(
//    private val apiService: YouTubeApi,
//    private val authManager: AuthManager
//) : YoutubeRepository {
//
//    override fun getPagedSubscriptions(order: String): Flow<PagingData<Subscription>> {
//        return Pager(
//            config = PagingConfig(pageSize = 20),
//            pagingSourceFactory = {
//                SubscriptionPagingSource(apiService, authManager, order)
//            }
//        ).flow.map { pagingData ->
//            pagingData.map { dto -> dto.toDomain() } // Map from DTO to Domain model
//        }
//    }
//
//    override suspend fun getChannelDetails(channelId: String): Result<ChannelDetails> {
//        return try {
//            val token = authManager.getFreshAccessToken()
//            val response = apiService.getChannelDetails("Bearer $token", id = channelId)
//            Result.Success(response.items.first().toDomain())
//        } catch (e: Exception) {
//            Result.Error("Failed to fetch details: ${e.message}")
//        }
//    }
//
//    // ... implement other methods similarly
//}