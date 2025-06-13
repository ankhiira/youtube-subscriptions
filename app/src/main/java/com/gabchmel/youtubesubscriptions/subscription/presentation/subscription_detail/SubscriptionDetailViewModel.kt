package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.subscription.domain.SubscriptionRepository
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_detail.model.SubscriptionDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionDetailState())
    val uiState = _uiState.asStateFlow()

    init {
        val subscriptionId: String? = savedStateHandle["subscriptionId"]
        if (subscriptionId != null) {
            fetchSubscriptionDetails(subscriptionId)
        } else {
            _uiState.update {
                it.copy(
                    error = "Subscription ID not found"
                )
            }
        }
    }

    private fun fetchSubscriptionDetails(subscriptionId: String) {
        viewModelScope.launch {
            try {
                val subscriptionDetail = subscriptionRepository.getSubscriptionDetails(subscriptionId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        subscriptionDetail = subscriptionDetail
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