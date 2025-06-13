package com.gabchmel.youtubesubscriptions.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gabchmel.youtubesubscriptions.auth.presentation.SignInScreen
import com.gabchmel.youtubesubscriptions.core.data.AuthHolder.account
import com.gabchmel.youtubesubscriptions.profile.presentation.ProfileScreen
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_detail.SubscriptionDetailScreen
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.SubscriptionListScreen

sealed class Screen(val route: String) {
    object SignIn : Screen("sign_in")
    object SubscriptionList : Screen("subscription_list")
    object SubscriptionDetail : Screen("subscription_detail/{subscriptionId}") {
        fun createRoute(subscriptionId: String) = "subscription_detail/$subscriptionId"
    }
    object UserProfile : Screen("user_profile")
}

@Composable
fun AppNavigation() {
    val userProfile by account.collectAsStateWithLifecycle()
    val isSignedIn = remember(userProfile) { userProfile != null }

    val navController = rememberNavController()
    val startDestination = if (isSignedIn) Screen.SubscriptionList.route else Screen.SignIn.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Sign in Screen
        composable(route = Screen.SignIn.route) {
            SignInScreen(
                navController = navController
            )
        }

        // Subscription List Screen
        composable(route = Screen.SubscriptionList.route) {
            SubscriptionListScreen(
                onSubscriptionClick = { subscriptionId ->
                    navController.navigate(Screen.SubscriptionDetail.createRoute(subscriptionId))
                },
                onProfileClick = {
                    navController.navigate(Screen.UserProfile.route)
                }
            )
        }

        // Subscription Detail Screen
        composable(
            route = Screen.SubscriptionDetail.route,
            arguments = listOf(navArgument("subscriptionId") { type = NavType.StringType })
        ) {
            SubscriptionDetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // User Profile Screen
        composable(route = Screen.UserProfile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onSignOut = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}