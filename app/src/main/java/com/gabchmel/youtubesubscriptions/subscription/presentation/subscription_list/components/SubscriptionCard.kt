package com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gabchmel.youtubesubscriptions.subscription.domain.model.Subscription

@Composable
fun SubscriptionCard(
    modifier: Modifier = Modifier,
    subscription: Subscription,
    onClick: () -> Unit
) {
    Card(modifier = modifier.fillMaxWidth(),
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