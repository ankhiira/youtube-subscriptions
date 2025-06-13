package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.gabchmel.youtubesubscriptions.subscription.domain.model.SortOrder

@Composable
fun SortActionMenu(
    onSortOrderSelected: (SortOrder) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { isMenuExpanded = true }) {
            Icon(Icons.Default.List, contentDescription = "Sort Subscriptions")
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false }
        ) {
            SortOrder.entries.forEach { sortOrder ->
                DropdownMenuItem(
                    text = { Text(sortOrder.name) },
                    onClick = {
                        onSortOrderSelected(sortOrder)
                        isMenuExpanded = false
                    }
                )
            }
        }
    }
}