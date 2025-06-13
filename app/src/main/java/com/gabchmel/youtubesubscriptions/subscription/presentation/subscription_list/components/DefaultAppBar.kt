package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model.SubscriptionListEvent
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model.SubscriptionListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    uiState: SubscriptionListState,
    onEvent: (SubscriptionListEvent) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "My Subscriptions") },
        actions = {
            // Search Icon
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Search Subscriptions")
            }

            // Sort Dropdown
            SortOrderDropdown(
                currentSortOrder = uiState.sortOrder,
                onSortOrderSelected = { newSort ->
                    onEvent(SubscriptionListEvent.ChangeSortOrder(newSort))
                }
            )

            // Profile Icon
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
        }
    )
}