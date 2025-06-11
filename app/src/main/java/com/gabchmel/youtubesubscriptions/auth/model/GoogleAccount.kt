package com.gabchmel.youtubesubscriptions.auth.model

data class GoogleAccount(
    val token: String,
    val displayName: String = "",
    val profileImageUrl: String? = null
)