package com.gabchmel.youtubesubscriptions.auth.presentation

import com.gabchmel.youtubesubscriptions.core.domain.UserProfile

data class SignInState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null
)
