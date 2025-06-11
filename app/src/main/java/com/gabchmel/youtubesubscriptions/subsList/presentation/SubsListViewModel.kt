package com.gabchmel.youtubesubscriptions.subsList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.subsList.data.SubscriptionRepository
import com.gabchmel.youtubesubscriptions.subsList.presentation.model.SubsListEvent
import com.gabchmel.youtubesubscriptions.subsList.presentation.model.SubsListUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SubsListViewModel(
    private val repository: SubscriptionRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(SubsListUiState())
    val uiState = _uiState.asStateFlow()

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
        }
    }

    private var fetchJob: Job? = null

    fun fetchSubscriptions() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val subscriptions = repository.getSubscriptions()
                _uiState.update {
                    it.copy(subscriptions = subscriptions)
                }
            } catch (ioe: IOException) {

            }
        }
    }
}