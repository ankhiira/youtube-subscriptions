package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.subscription.domain.SubscriptionRepository
import com.gabchmel.youtubesubscriptions.subscription.domain.model.SortOrder
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model.SubscriptionListEvent
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model.SubscriptionListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionListViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionListState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        onEvent(SubscriptionListEvent.LoadSubscriptions)
    }

    fun onEvent(event: SubscriptionListEvent) {
        when (event) {
            is SubscriptionListEvent.LoadSubscriptions -> {
                fetchSubscriptions()
            }

            is SubscriptionListEvent.ChangeSortOrder -> {
                changeSortOrder(event.newSort)
                fetchSubscriptions(event.newSort)
            }

            is SubscriptionListEvent.SearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.newQuery) }
                applyFiltersAndSort()
            }
        }
    }

    private fun fetchSubscriptions(sortOrder: SortOrder = SortOrder.RELEVANCE) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val subscriptions = subscriptionRepository.getSubscriptions(sortOrder)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        subscriptions = subscriptions,
                        filteredSubscriptions = subscriptions
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load subscriptions: ${e.message}"
                    )
                }
            }
        }
    }

    private fun applyFiltersAndSort() {
        val currentState = _uiState.value

        val filteredList = if (currentState.searchQuery.isBlank()) {
            _uiState.value.subscriptions
        } else {
            _uiState.value.subscriptions.filter { subscription ->
                subscription.title.contains(currentState.searchQuery, ignoreCase = true)
            }
        }

        _uiState.update {
            it.copy(filteredSubscriptions = filteredList)
        }
    }

    private fun changeSortOrder(newSortOrder: SortOrder) {
        _uiState.update {
            it.copy(
                sortOrder = newSortOrder
            )
        }
    }
}