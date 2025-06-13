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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBarContent(
    onEvent: (SubscriptionListEvent) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "My Subscriptions") },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Search Subscriptions")
            }

            SortActionMenu(
                onSortOrderSelected = { newOrder ->
                    onEvent(SubscriptionListEvent.ChangeSortOrder(newOrder))
                }
            )

            IconButton(onClick = onProfileClick) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
        }
    )
}