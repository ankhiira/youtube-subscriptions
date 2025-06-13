package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.components.DefaultAppBar
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.components.SearchAppBar
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.components.SubscriptionCard
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model.SubscriptionListEvent
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model.SubscriptionListState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubscriptionListScreen(
    viewModel: SubscriptionListViewModel = koinViewModel(),
    onSubscriptionClick: (subscriptionId: String) -> Unit,
    onProfileClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SubsListScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onSubscriptionClick = onSubscriptionClick,
        onProfileClick = onProfileClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubsListScreenContent(
    modifier: Modifier = Modifier,
    uiState: SubscriptionListState,
    onEvent: (SubscriptionListEvent) -> Unit,
    onSubscriptionClick: (subscriptionId: String) -> Unit,
    onProfileClick: () -> Unit
) {
    var isSearchActive by remember { mutableStateOf(false) }

    val items = if (isSearchActive) uiState.filteredSubscriptions
        else uiState.subscriptions

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (isSearchActive) {
                SearchAppBar(
                    query = uiState.searchQuery,
                    onQueryChange = { newQuery -> onEvent(SubscriptionListEvent.SearchQueryChanged(newQuery)) },
                    onClose = { isSearchActive = false }
                )
            } else {
                DefaultAppBar(
                    uiState = uiState,
                    onEvent = onEvent,
                    onSearchClick = { isSearchActive = true },
                    onProfileClick = onProfileClick
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error,
                        modifier = Modifier
                            .align(Alignment.Center),
                        color = Color.Red
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(items, key = { it.id }) { subscription ->
                            SubscriptionCard(
                                subscription = subscription,
                                onClick = {
                                    onSubscriptionClick(subscription.id)
//                                    onEvent(SubsListEvent.SubscriptionClicked(subscription))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}