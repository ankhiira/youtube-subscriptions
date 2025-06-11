package com.gabchmel.youtubesubscriptions.subsList.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabchmel.youtubesubscriptions.subsList.presentation.components.SubscriptionItem
import com.gabchmel.youtubesubscriptions.subsList.presentation.model.SubsListEvent
import com.gabchmel.youtubesubscriptions.subsList.presentation.model.SubsListUiState
import com.gabchmel.youtubesubscriptions.ui.theme.YoutubeSubscriptionsTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubsListScreen(
    viewModel: SubsListViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SubsListScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubsListScreenContent(
    modifier: Modifier = Modifier,
    uiState: SubsListUiState,
    onEvent: (SubsListEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Subscriptions")
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(uiState.subscriptions, key = { it.id }) { subscription ->
                    SubscriptionItem(
                        subscription = subscription,
                        onClick = {
                            onEvent(SubsListEvent.SubscriptionClicked(subscription))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubsListScreenContentPreview() {
    YoutubeSubscriptionsTheme {
        SubsListScreenContent(
            uiState = SubsListUiState(),
            onEvent = {}
        )
    }
}