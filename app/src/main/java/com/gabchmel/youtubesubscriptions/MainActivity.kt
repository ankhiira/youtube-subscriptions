package com.gabchmel.youtubesubscriptions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gabchmel.youtubesubscriptions.navigation.AppNavigation
import com.gabchmel.youtubesubscriptions.ui.theme.SubscriptionsTheme

class MainActivity : ComponentActivity() {
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