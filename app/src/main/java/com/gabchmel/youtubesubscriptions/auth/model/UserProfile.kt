package com.gabchmel.youtubesubscriptions.auth.model

data class UserProfile(
    val name: String,
    val email: String,
    val photoUrl: String?
)