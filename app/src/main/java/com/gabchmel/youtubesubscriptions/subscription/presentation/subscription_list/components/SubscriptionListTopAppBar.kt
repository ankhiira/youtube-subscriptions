package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model.SubscriptionListEvent
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.model.SubscriptionListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionListTopAppBar(
    uiState: SubscriptionListState,
    onEvent: (SubscriptionListEvent) -> Unit,
    isSearchActive: Boolean,
    onToggleSearch: () -> Unit,
    onProfileClick: () -> Unit
) {
    AnimatedContent(
        targetState = isSearchActive,
        label = "TopAppBarAnimation",
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }
    ) { searchIsActive ->
        if (searchIsActive) {
            SearchAppBarContent(
                query = uiState.searchQuery,
                onQueryChange = { newQuery ->
                    onEvent(
                        SubscriptionListEvent.SearchQueryChanged(
                            newQuery
                        )
                    )
                },
                onClose = onToggleSearch
            )
        } else {
            DefaultAppBarContent(
                onEvent = onEvent,
                onSearchClick = onToggleSearch,
                onProfileClick = onProfileClick
            )
        }
    }
}