package com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.model.SubscriptionUiState

@Composable
fun SubscriptionItem(
    modifier: Modifier = Modifier,
    subscription: SubscriptionUiState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        AsyncImage(
//            model = subscription.channelImageUrl,
//            contentDescription = "${subscription.channelName} channel icon",
//            placeholder = painterResource(id = R.drawable.ic_launcher_background),
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.size(56.dp).clip(CircleShape)
//        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = subscription.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SubscriptionCard(
    modifier: Modifier = Modifier,
    subscription: SubscriptionUiState,
    onClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(),
        onClick = onClick) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = subscription.thumbnailUrl,
                contentDescription = "Thumbnail",
                modifier = Modifier.size(56.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(subscription.title, fontWeight = FontWeight.Bold)
        }
    }
}