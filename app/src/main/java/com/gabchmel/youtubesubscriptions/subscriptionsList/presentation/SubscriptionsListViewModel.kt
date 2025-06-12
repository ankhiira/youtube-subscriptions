package com.gabchmel.youtubesubscriptions.subscriptionsList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.profile.presentation.AuthHolder
import com.gabchmel.youtubesubscriptions.subscriptionsList.data.model.SubscriptionItemDto
import com.gabchmel.youtubesubscriptions.subscriptionsList.data.YoutubeApi
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model.SortOrder
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model.SubsListEvent
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model.SubscriptionsListUiState
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model.SubscriptionUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionsListViewModel(
//    private val repository: YoutubeRepository
    private val youtubeApi: YoutubeApi
): ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionsListUiState())
    val uiState = _uiState.asStateFlow()

//    val subscriptionPages: Flow<PagingData<Subscription>> = _sortOrder.flatMapLatest { order ->
////        getSubscriptionsUseCase(order.value)
//    }.cachedIn(viewModelScope)

//    val subscriptionPages: Flow<PagingData<SubscriptionItem>> = accessToken.filterNotNull().flatMapLatest { token ->
//        Pager(
//            config = PagingConfig(pageSize = 20), // How many items to load per page
//            pagingSourceFactory = {
//                SubscriptionPagingSource(
//                    youtubeApiService = apiService,
//                    accessToken = token
//                )
//            }
//        ).flow
//    }.cachedIn(viewModelScope)

    init {
        onEvent(SubsListEvent.LoadSubscriptions)
    }

    fun onEvent(event: SubsListEvent) {
        when (event) {
            is SubsListEvent.LoadSubscriptions -> {
                fetchSubscriptions()
            }
            is SubsListEvent.SubscriptionClicked -> {
                //TODO navigate to detail screen
            }

            is SubsListEvent.ChangeSortOrder -> {
                changeSortOrder(event.newSort)
                fetchSubscriptions(event.newSort)
            }

            is SubsListEvent.SearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.newQuery) }
                applyFiltersAndSort()
            }
        }
    }

    private fun changeSortOrder(newSortOrder: SortOrder) {
        _uiState.update {
            it.copy(
                sortOrder = newSortOrder
            )
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

    private var fetchJob: Job? = null

    fun fetchSubscriptions(sortOrder: SortOrder = SortOrder.RELEVANCE) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val accessToken = AuthHolder.accessToken.value

            if (accessToken == null) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Not signed in.")
                }
                return@launch
            }

            try {
                val response = youtubeApi.getSubscriptions(
                    authHeader = "Bearer $accessToken",
                    order = when (sortOrder) {
                        SortOrder.RELEVANCE -> "relevance"
                        SortOrder.ALPHABETICALLY -> "alphabetical"
                    }
                )

                // Map the DTOs from the API to the UI models our screen needs
                val uiSubscriptions = response.items.map { it.toSubscriptionUiState() }

                // Update the state with the new data
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        subscriptions = uiSubscriptions,
                        filteredSubscriptions = uiSubscriptions
                    )
                }
            } catch (e: Exception) {
                // This will catch network errors, 401 Unauthorized (bad token), etc.
                _uiState.update {
                    it.copy(isLoading = false, error = "Failed to load subscriptions: ${e.message}")
                }
            }
        }
    }
}

fun SubscriptionItemDto.toSubscriptionUiState(): SubscriptionUiState {
    return SubscriptionUiState(
        id = this.id,
        title = this.snippet.title,
        thumbnailUrl = this.snippet.thumbnails.high.url // Use the high-res thumbnail
    )
}