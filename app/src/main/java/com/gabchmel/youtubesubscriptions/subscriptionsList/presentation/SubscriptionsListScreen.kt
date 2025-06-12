package com.gabchmel.youtubesubscriptions.subscriptionsList.presentation

import SortOrderDropdown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.components.DefaultAppBar
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.components.SearchAppBar
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.components.SubscriptionCard
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model.SubsListEvent
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model.SubscriptionsListUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubscriptionsListScreen(
    viewModel: SubscriptionsListViewModel = koinViewModel(),
    onSubscriptionClick: (channelId: String) -> Unit,
    onProfileClick: () -> Unit
) {

//    val lazyPagingItems = viewModel.subscriptionPages.collectAsLazyPagingItems()

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
    uiState: SubscriptionsListUiState,
    onEvent: (SubsListEvent) -> Unit,
    onSubscriptionClick: (channelId: String) -> Unit,
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
                    onQueryChange = { newQuery -> onEvent(SubsListEvent.SearchQueryChanged(newQuery)) },
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
                    androidx.compose.material3.CircularProgressIndicator(
                        modifier = Modifier
                            .align(androidx.compose.ui.Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error,
                        modifier = Modifier
                            .align(androidx.compose.ui.Alignment.Center),
                        color = androidx.compose.ui.graphics.Color.Red
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

//@Preview(showBackground = true)
//@Composable
//fun SubsListScreenContentPreview() {
//    SubscriptionsTheme {
//        SubsListScreenContent(
//            uiState = SubsListUiState(
//                subscriptions = listOf(
//                    SubscriptionUiState(
//                        id = "1",
//                        title = "test",
//                        thumbnailUrl = ""
//                    )
//                )
//            ),
//            onEvent = {}
//        )
//    }
//}