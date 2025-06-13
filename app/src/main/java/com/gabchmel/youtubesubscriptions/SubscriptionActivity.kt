package com.gabchmel.youtubesubscriptions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gabchmel.youtubesubscriptions.core.presentation.navigation.AppNavigation
import com.gabchmel.youtubesubscriptions.core.presentation.theme.SubscriptionsTheme

class SubscriptionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SubscriptionsTheme {
                AppNavigation()
            }
        }
    }
}