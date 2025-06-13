package com.gabchmel.youtubesubscriptions.core.domain

data class UserProfile(
    val name: String,
    val email: String,
    val photoUrl: String?
)