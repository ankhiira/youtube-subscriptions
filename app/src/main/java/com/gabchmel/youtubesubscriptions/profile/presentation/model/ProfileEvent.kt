package com.gabchmel.youtubesubscriptions.profile.presentation.model

sealed class ProfileEvent {
    data object SignOut : ProfileEvent()
}