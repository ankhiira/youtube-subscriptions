package com.gabchmel.youtubesubscriptions.auth.data

import com.gabchmel.youtubesubscriptions.core.domain.UserProfile

data class SignInResult(
    val user: UserProfile?,
    val errorMessage: String?
) {
    val isSuccess: Boolean get() = user != null
}